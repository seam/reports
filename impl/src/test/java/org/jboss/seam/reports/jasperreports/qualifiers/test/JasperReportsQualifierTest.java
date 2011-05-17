package org.jboss.seam.reports.jasperreports.qualifiers.test;

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
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.annotations.frameworks.JasperReports;
import org.jboss.seam.reports.annotations.output.PDF;
import org.jboss.seam.reports.jasperreports.JasperSeamReportDataSource;
import org.jboss.seam.reports.jasperreports.JasperSeamReportLoader;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.oio.jpdfunit.DocumentTester;

@RunWith(Arquillian.class)
public class JasperReportsQualifierTest {

    @Inject
    @SalesReport
    Report salesReport;

    @Inject
    @Resource("XlsDataSource.data.xls")
    InputStream dataSource;

    @Inject
    @JasperReports
    @PDF
    ReportRenderer pdfRenderer;

    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.jboss.seam.solder")
                .addPackages(true, "org.jboss.seam.reports")
                .addPackages(true, "org.jboss.seam.reports.annotations")
                .addPackages(true, "org.jboss.seam.reports.jasperreports")
                .addClass(JasperSeamReportLoader.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Test
    public void testReportLifecycle() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        // Preparing parameters
        params.put("ReportTitle", "Address Report");
        params.put("DataFile", "XlsDataSource.data.xls - XLS data source");
        Set<String> states = new HashSet<String>();
        states.add("Active");
        states.add("Trial");
        params.put("IncludedStates", states);

        ReportInstance reportInstance = salesReport.fill(getDataSource(), params);

        ByteArrayOutputStream os = new ByteArrayOutputStream(); // OutputStream
        // Render output as the desired content
        pdfRenderer.render(reportInstance, os);
        DocumentTester tester = new DocumentTester(new ByteArrayInputStream(os.toByteArray()));
        try {
            tester.assertPageCountEquals(2);
        } finally {
            tester.close();
        }
    }

    private ReportDataSource getDataSource() throws Exception {
        JRXlsDataSource ds;
        String[] columnNames = new String[] { "city", "id", "name", "address", "state" };
        int[] columnIndexes = new int[] { 0, 2, 3, 4, 5 };
        ds = new JRXlsDataSource(dataSource);
        ds.setColumnNames(columnNames, columnIndexes);
        return new JasperSeamReportDataSource(ds);
    }
}