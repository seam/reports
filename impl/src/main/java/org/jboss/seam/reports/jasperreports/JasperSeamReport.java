package org.jboss.seam.reports.jasperreports;

import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.jboss.seam.reports.SeamReport;
import org.jboss.seam.reports.SeamReportDataSource;
import org.jboss.seam.reports.SeamReportException;
import org.jboss.seam.reports.SeamReportInstance;

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
    public SeamReportInstance fill(SeamReportDataSource dataSource, Map<String, Object> parameters) throws SeamReportException {
        try {
            JasperPrint filledReport = JasperFillManager.fillReport(getCompiledReport(), parameters);
            return new JasperSeamReportInstance(filledReport);
        } catch (JRException e) {
            throw new SeamReportException(e);
        }
    }

}
