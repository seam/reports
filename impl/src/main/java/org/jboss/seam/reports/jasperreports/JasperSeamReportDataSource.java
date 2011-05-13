package org.jboss.seam.reports.jasperreports;

import net.sf.jasperreports.engine.JRDataSource;

import org.jboss.seam.reports.SeamReportDataSource;

public class JasperSeamReportDataSource implements SeamReportDataSource {
    private JRDataSource dataSource;

    public JasperSeamReportDataSource(JRDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JRDataSource getDataSource() {
        return dataSource;
    }
}
