package test.ejb;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import org.junit.Test;

import tech.lapsa.insurance.notificationDaemon.template.NotificationMessages;
import tech.lapsa.insurance.notificationDaemon.template.NotificationTemplates;
import tech.lapsa.insurance.notificationDaemon.template.InsuranceTemplateProvider.InsuranceTemplateProviderLocal;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.logging.MyLogger;
import tech.lapsa.java.commons.logging.MyLogger.MyLevel;

public class InsuranceTemplateProviderTest extends ArquillianBaseTestCase {

    @Inject
    private InsuranceTemplateProviderLocal templates;

    private static final MyLevel logger = MyLogger.newBuilder() //
	    .withNameOf(InsuranceTemplateProviderTest.class) //
	    .build() //
		    .INFO;

    private static final Collection<Locale> LOCALES = MyCollections.unmodifiableOrEmptyList(
	    Arrays.asList(Locale.ENGLISH, Locale.forLanguageTag("ru"), Locale.forLanguageTag("kk")));

    @Test
    public void testMessages() throws IllegalArgument {
	for (Locale locale : LOCALES)
	    for (NotificationMessages message : NotificationMessages.values()) {
		final String msg = templates.getMessage(message, locale);
		assertThat(msg, not(isEmptyOrNullString()));
		logger.log(msg);
	    }
    }

    @Test
    public void testTemplates() throws IllegalArgument {
	for (Locale locale : LOCALES)
	    for (NotificationTemplates message : NotificationTemplates.values()) {
		final String msg = templates.getTemplate(message, locale);
		assertThat(msg, not(isEmptyOrNullString()));
		logger.log(msg);
	    }
    }

}
