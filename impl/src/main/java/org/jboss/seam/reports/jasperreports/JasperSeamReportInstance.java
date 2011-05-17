package org.jboss.seam.reports.jasperreports;

import net.sf.jasperreports.engine.JasperPrint;

import org.jboss.seam.reports.ReportInstance;

public class JasperSeamReportInstance implements ReportInstance {

    private JasperPrint jasperPrint;

    public JasperSeamReportInstance(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }
}
