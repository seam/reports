package org.jboss.seam.reports.jasperreports;

import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportInstance;
import org.jboss.seam.reports.SeamReportOutputType;

public class JasperSeamReportInstance implements ReportInstance {

    private JasperPrint jasperPrint;

    public JasperSeamReportInstance(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    @Override
    public void render(SeamReportOutputType type, OutputStream output) throws ReportException {
        try {
            JasperExportManager.exportReportToPdfStream(getJasperPrint(), output);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public boolean isOutputTypeSupported(SeamReportOutputType type) {
        // TODO Auto-generated method stub
        return false;
    }

}
