package test.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.runner.RunWith;

import tech.lapsa.eurasia36.notificationDaemon.template.beans.EpaymentTemplateProviderBean;
import tech.lapsa.lapsa.arquillian.archive.ArchiveBuilderFactory;

@RunWith(Arquillian.class)
public abstract class ArquillianBaseTestCase {

    private static final Archive<?> DEPLOYMENT = ArchiveBuilderFactory.newEarBuilder() //
	    .withModule(ArchiveBuilderFactory.newEjbBuilder() //
		    .withPackageOf(EpaymentTemplateProviderBean.class) //
		    .withManifestFolder() //
		    .build() //
		    .dumpingTo(System.out::println)) //
	    .withRuntimeDependencies()
	    .build() //
	    .dumpingTo(System.out::println) //
	    .asEnterpriseArchive();

    @Deployment(testable = true)
    public static Archive<?> createDeploymentEAR() {
	return DEPLOYMENT;
    }
}
