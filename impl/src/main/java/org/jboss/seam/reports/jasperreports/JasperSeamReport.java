package org.jboss.seam.reports.jasperreports;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportDataSource;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportInstance;

public class JasperSeamReport implements Report<ReportDataSource, ReportInstance> {

    private JasperReport compiledReport;

    public JasperSeamReport(JasperReport compiledReport) {
        super();
        this.compiledReport = compiledReport;
    }

    public JasperReport getCompiledReport() {
        return compiledReport;
    }

    @Override
    public ReportInstance fill(ReportDataSource dataSource, Map<String, Object> parameters) throws ReportException {
        try {
            JRDataSource ds = null;
            if (dataSource instanceof JasperSeamReportDataSource) {
                ds = ((JasperSeamReportDataSource)dataSource).getDataSource();
            }
            JasperPrint filledReport = JasperFillManager.fillReport(getCompiledReport(), parameters,ds);
            return new JasperSeamReportInstance(filledReport);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

}
