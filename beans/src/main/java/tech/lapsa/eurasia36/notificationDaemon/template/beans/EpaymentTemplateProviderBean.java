package tech.lapsa.eurasia36.notificationDaemon.template.beans;

import java.util.Locale;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import tech.lapsa.epayment.notificationDaemon.template.EpaymentTemplateProvider.EpaymentTemplateProviderLocal;
import tech.lapsa.epayment.notificationDaemon.template.EpaymentTemplateProvider.EpaymentTemplateProviderRemote;
import tech.lapsa.epayment.notificationDaemon.template.NotificationMessages;
import tech.lapsa.epayment.notificationDaemon.template.NotificationTemplates;
import tech.lapsa.java.commons.exceptions.IllegalArgument;

@Singleton
public class EpaymentTemplateProviderBean extends BaseTemplateProvider
	implements EpaymentTemplateProviderLocal, EpaymentTemplateProviderRemote {

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
