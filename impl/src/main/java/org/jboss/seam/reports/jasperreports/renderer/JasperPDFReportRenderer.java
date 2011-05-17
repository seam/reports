package org.jboss.seam.reports.jasperreports.renderer;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.jboss.seam.reports.annotations.JasperReports;
import org.jboss.seam.reports.annotations.PDF;

@JasperReports
@PDF
public class JasperPDFReportRenderer extends AbstractJasperReportRenderer {
    @Override
    protected JRExporter getExporter() {
        return new JRPdfExporter();
    }
}
