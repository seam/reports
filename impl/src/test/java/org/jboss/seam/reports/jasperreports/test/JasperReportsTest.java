package org.jboss.seam.reports.jasperreports.test;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import net.sf.jasperreports.engine.data.JRXlsDataSource;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportDataSource;
import org.jboss.seam.reports.ReportInstance;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.annotations.JasperReports;
import org.jboss.seam.reports.annotations.PDF;
import org.jboss.seam.reports.jasperreports.JasperSeamReportDataSource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.oio.jpdfunit.DocumentTester;

@RunWith(Arquillian.class)
public class JasperReportsTest {

    @Inject
    @JasperReports
    ReportLoader loader;

    @Inject
    @JasperReports
    @PDF
    ReportRenderer pdfRenderer;

    // @Inject
    // @JasperReports
    // @XLS
    // ReportRenderer xlsRenderer;

    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap.create(JavaArchive.class).addPackage("org.jboss.seam.reports")
                .addPackage("org.jboss.seam.reports.annotation").addPackage("org.jboss.seam.reports.jasperreports")
                .addPackage("org.jboss.seam.reports.jasperreports.renderer")
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Test
    public void testLoaderNotNull() throws Exception {
        assertNotNull(loader);
    }

    @Test
    public void testReportLifecycle() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("XlsDataSourceReport.jrxml"); // Report
        // source
        Report report = loader.compile(is);

        Map<String, Object> params = new HashMap<String, Object>();
        // Preparing parameters
        params.put("ReportTitle", "Address Report");
        params.put("DataFile", "XlsDataSource.data.xls - XLS data source");
        Set<String> states = new HashSet<String>();
        states.add("Active");
        states.add("Trial");
        params.put("IncludedStates", states);

        ReportInstance reportInstance = report.fill(getDataSource(), params);

        ByteArrayOutputStream os = new ByteArrayOutputStream(); // OutputStream
        // Render output as the desired content
        pdfRenderer.render(reportInstance, os);
        DocumentTester tester = new DocumentTester(new ByteArrayInputStream(os.toByteArray()));
        tester.assertPageCountEquals(2);
    }

    private ReportDataSource getDataSource() throws Exception {
        JRXlsDataSource ds;
        String[] columnNames = new String[] { "city", "id", "name", "address", "state" };
        int[] columnIndexes = new int[] { 0, 2, 3, 4, 5 };
        ds = new JRXlsDataSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("XlsDataSource.data.xls"));
        ds.setColumnNames(columnNames, columnIndexes);

        return new JasperSeamReportDataSource(ds);
    }

}
