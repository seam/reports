package org.jboss.seam.reports.jasperreports;

import net.sf.jasperreports.engine.JRDataSource;

import org.jboss.seam.reports.ReportDataSource;

@JasperReports
public class JasperSeamReportDataSource implements ReportDataSource {
    private JRDataSource dataSource;

    public JasperSeamReportDataSource(JRDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JRDataSource getDataSource() {
        return dataSource;
    }
}
