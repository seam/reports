package org.jboss.seam.reports.jasperreports;

import java.util.Map;

import net.sf.jasperreports.engine.JasperReport;

import org.jboss.seam.reports.SeamReport;
import org.jboss.seam.reports.SeamReportDataSource;
import org.jboss.seam.reports.SeamReportException;
import org.jboss.seam.reports.SeamReportPrint;

public class JasperSeamReport implements SeamReport {

    private JasperReport compiledReport;

    public JasperSeamReport(JasperReport compiledReport) {
        super();
        this.compiledReport = compiledReport;
    }

    public JasperReport getCompiledReport() {
        return compiledReport;
    }

    @Override
    public SeamReportPrint fill(SeamReportDataSource dataSource, Map<String, Object> parameters) throws SeamReportException {
        // TODO Auto-generated method stub
        return null;
    }

}
