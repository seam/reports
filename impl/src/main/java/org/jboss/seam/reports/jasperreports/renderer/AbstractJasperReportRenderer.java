package org.jboss.seam.reports.jasperreports.renderer;

import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.jasperreports.JasperSeamReportInstance;

public abstract class AbstractJasperReportRenderer implements ReportRenderer<JasperSeamReportInstance> {

    @Override
    public void render(JasperSeamReportInstance reportInstance, OutputStream output) throws ReportException {
        JRExporter exporter = getExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, reportInstance.getJasperPrint());
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
        try {
            exporter.exportReport();
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

    /**
     * Returns the exporter
     * 
     * @return
     */
    protected abstract JRExporter getExporter();

}
