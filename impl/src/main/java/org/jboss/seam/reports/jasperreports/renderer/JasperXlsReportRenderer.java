package org.jboss.seam.reports.jasperreports.renderer;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.jboss.seam.reports.annotations.JasperReports;
import org.jboss.seam.reports.annotations.XLS;

@JasperReports
@XLS
public class JasperXlsReportRenderer extends AbstractJasperReportRenderer {

    @Override
    protected JRExporter getExporter() {
        return new JRXlsExporter();
    }
}
