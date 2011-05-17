package org.jboss.seam.reports.jasperreports.renderer;

import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportInstance;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.jasperreports.JasperSeamReportInstance;

public abstract class AbstractJasperReportRenderer implements ReportRenderer {

    @Override
    public void render(ReportInstance report, OutputStream output) throws ReportException {
        JasperSeamReportInstance instance = (JasperSeamReportInstance) report;
        JRExporter exporter = getExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, instance.getJasperPrint());
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
