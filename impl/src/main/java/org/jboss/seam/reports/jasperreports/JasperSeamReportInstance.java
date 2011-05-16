package org.jboss.seam.reports.jasperreports;

import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.jboss.seam.reports.SeamReportException;
import org.jboss.seam.reports.SeamReportInstance;
import org.jboss.seam.reports.SeamReportOutputType;

public class JasperSeamReportInstance implements SeamReportInstance {

    private JasperPrint jasperPrint;

    public JasperSeamReportInstance(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    @Override
    public void render(SeamReportOutputType type, OutputStream output) throws SeamReportException {
        try {
            JasperExportManager.exportReportToPdfStream(getJasperPrint(), output);
        } catch (JRException e) {
            throw new SeamReportException(e);
        }
    }

    @Override
    public boolean isOutputTypeSupported(SeamReportOutputType type) {
        // TODO Auto-generated method stub
        return false;
    }

}
