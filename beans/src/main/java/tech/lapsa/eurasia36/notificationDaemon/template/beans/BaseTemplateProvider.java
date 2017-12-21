package tech.lapsa.eurasia36.notificationDaemon.template.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.EJBException;

import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.io.MyResources;
import tech.lapsa.java.commons.util.MyResourceBundles;

public abstract class BaseTemplateProvider {

    protected <T extends Enum<?>> String _getTemplate(final T entity, final Locale locale) {
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

    protected <T extends Enum<?>> String _getMessage(final T entity, final Locale locale)
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