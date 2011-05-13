package org.jboss.seam.reports;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.reports.annotations.JasperReports;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ReportsTest {
    @Inject
    @JasperReports
    SeamReportLoader loader;

    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap.create(JavaArchive.class).addPackage("org.jboss.seam.reports")
                .addPackage("org.jboss.seam.reports.annotation").addPackage("org.jboss.seam.reports.jasperreports")
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Test
    public void testLoaderNotNull() throws Exception {
        assertNotNull(loader);
    }

    @Test
    public void testCompilation() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jasper_template.xml"); // Report
                                                                                                                    // source
        SeamReport report = loader.compile(is);
        Map<String,Object> params = new HashMap<String, Object>();
        SeamReportInstance reportInstance = report.fill(getDataSource(), params);

        ByteArrayOutputStream os = new ByteArrayOutputStream(); // OutputStream
        // Render output as the desired content
        reportInstance.render(SeamReportOutputType.PDF, os);
        
        assertTrue("OutputStream is not empty", os.size() > 0);

    }

    private SeamReportDataSource getDataSource() {
        return null;
    }

}
