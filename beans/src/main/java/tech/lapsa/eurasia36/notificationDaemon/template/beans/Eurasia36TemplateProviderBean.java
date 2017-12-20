package tech.lapsa.eurasia36.notificationDaemon.template.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import tech.lapsa.epayment.notificationDaemon.template.NotificationTemplates;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.io.MyResources;
import tech.lapsa.java.commons.util.MyResourceBundles;

@Singleton
public class Eurasia36TemplateProviderBean implements
	tech.lapsa.epayment.notificationDaemon.template.TemplateProvider.TemplateProviderLocal,
	tech.lapsa.epayment.notificationDaemon.template.TemplateProvider.TemplateProviderRemote,
	tech.lapsa.insurance.notificationDaemon.template.TemplateProvider.TemplateProviderLocal,
	tech.lapsa.insurance.notificationDaemon.template.TemplateProvider.TemplateProviderRemote {

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getMessage(final tech.lapsa.insurance.notificationDaemon.template.NotificationMessages message,
	    Locale locale) throws IllegalArgument {
	try {
	    return _getMessage(message, locale);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getTemplate(final tech.lapsa.insurance.notificationDaemon.template.NotificationTemplates message,
	    Locale locale) throws IllegalArgument {
	try {
	    return _getTemplate(message, locale);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getMessage(final tech.lapsa.epayment.notificationDaemon.template.NotificationMessages message,
	    Locale locale) throws IllegalArgument {
	try {
	    return _getMessage(message, locale);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getTemplate(final NotificationTemplates message, final Locale locale) throws IllegalArgument {
	try {
	    return _getTemplate(message, locale);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    // PRIVATE

    private <T extends Enum<?>> String _getTemplate(final T entity, final Locale locale) {
	final String templateName = _getMessage(entity, locale);

	try (final InputStream is = MyResources.getAsStream(this.getClass(), templateName);
		final InputStreamReader isr = new InputStreamReader(is);
		final StringWriter sw = new StringWriter()) {
	    int readed = -1;
	    char[] cbuff = new char[256];
	    while ((readed = isr.read(cbuff)) > 0)
		sw.write(cbuff, 0, readed);
	    return sw.toString();
	} catch (IOException e) {
	    // it should not happens
	    throw new EJBException(e.getMessage(), e);
	}
    }

    private <T extends Enum<?>> String _getMessage(final T entity, final Locale locale)
	    throws IllegalArgumentException {

	MyObjects.requireNonNull(entity, "entity");
	MyObjects.requireNonNull(locale, "locale");

	final String baseName = entity.getClass().getName();
	final ResourceBundle bundle = MyResourceBundles.of(entity.getClass(), baseName, locale);
	final String key = MyStrings.format("%1$s.%2$s", entity.getClass().getName(), entity.name());
	if (MyObjects.isNull(bundle))
	    // it should not happens
	    throw MyExceptions.format(EJBException::new, "Bundle is null");
	if (MyStrings.empty(key))
	    // it should not happens
	    throw MyExceptions.format(EJBException::new, "Key is empty");
	try {
	    return bundle.getString(key);
	} catch (final Exception e) {
	    // it should not happens
	    throw new EJBException(e.getMessage());
	}
    }

}
