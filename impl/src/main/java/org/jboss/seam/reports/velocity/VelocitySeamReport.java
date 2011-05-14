package org.jboss.seam.reports.velocity;

import java.io.OutputStream;
import java.util.Map;

import org.apache.velocity.Template;
import org.jboss.seam.reports.SeamReport;
import org.jboss.seam.reports.SeamReportDataSource;
import org.jboss.seam.reports.SeamReportException;
import org.jboss.seam.reports.SeamReportInstance;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class VelocitySeamReport implements SeamReport {
    private Template template;
    
    public VelocitySeamReport(Template template) {
        this.template = template;
    }
    
    public Template getTemplate() {
        return template;
    }
    
    @Override   
    public SeamReportInstance fill(SeamReportDataSource dataSource, Map<String, Object> parameters) throws SeamReportException {
        return null;
    }
    /*
    public void toPDF(String contents, OutputStream out)  throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(contents);
        renderer.layout();
        renderer.createPDF(out);        
    }*/

}
