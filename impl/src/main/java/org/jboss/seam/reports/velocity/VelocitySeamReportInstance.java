package org.jboss.seam.reports.velocity;

import java.io.OutputStream;

import org.jboss.seam.reports.SeamReportException;
import org.jboss.seam.reports.SeamReportInstance;
import org.jboss.seam.reports.SeamReportOutputType;

public class VelocitySeamReportInstance implements SeamReportInstance {

    private String mergedTemplate;
    
    public VelocitySeamReportInstance(String mergedTemplate) {
        this.mergedTemplate = mergedTemplate;
    }
    
    public String getMergedTemplate() {
        return mergedTemplate;
    }
    
    @Override
    public boolean isOutputTypeSupported(SeamReportOutputType type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void render(SeamReportOutputType type, OutputStream output) throws SeamReportException {
        // TODO Auto-generated method stub

    }

}
