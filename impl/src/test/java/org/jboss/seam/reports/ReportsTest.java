package org.jboss.seam.reports;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.jboss.seam.reports.annotations.JasperReports;
import org.junit.Test;

public class ReportsTest {
    @Inject
    @JasperReports
    SeamReportLoader compiler;

    @Test
    public void testCompilation() throws Exception {
        InputStream is = null; // Report source
        SeamReport report = compiler.compile(is);
        SeamReportInstance print = report.fill(getDataSource(), null);

        OutputStream os = new ByteArrayOutputStream(); // OutputStream
        // Render output as the desired content
        print.render(SeamReportOutputType.PDF,os);
    
    }
    
    private SeamReportDataSource getDataSource() {
        return null;
    }

}
