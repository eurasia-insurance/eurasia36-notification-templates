package tech.lapsa.eurasia36.notificationDaemon.template.beans;

import java.util.Locale;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import tech.lapsa.insurance.notificationDaemon.template.InsuranceTemplateProvider.InsuranceTemplateProviderLocal;
import tech.lapsa.insurance.notificationDaemon.template.InsuranceTemplateProvider.InsuranceTemplateProviderRemote;
import tech.lapsa.insurance.notificationDaemon.template.NotificationMessages;
import tech.lapsa.insurance.notificationDaemon.template.NotificationTemplates;
import tech.lapsa.java.commons.exceptions.IllegalArgument;

@Singleton
public class InsuranceTemplateProviderBean extends BaseTemplateProvider
	implements InsuranceTemplateProviderLocal, InsuranceTemplateProviderRemote {

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getMessage(final NotificationMessages message, final Locale locale) throws IllegalArgument {
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
}
