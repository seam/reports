package org.jboss.seam.reports.velocity;

import java.io.InputStream;

import org.jboss.seam.reports.SeamReport;
import org.jboss.seam.reports.SeamReportException;
import org.jboss.seam.reports.SeamReportInstance;
import org.jboss.seam.reports.SeamReportLoader;
import org.jboss.seam.reports.annotations.Velocity;

@Velocity
public class VelocitySeamReportLoader implements SeamReportLoader {

    @Override
    public SeamReport compile(InputStream input) throws SeamReportException {
        return null;
    }

    @Override
    public SeamReport loadReport(InputStream input) throws SeamReportException {
        return null;
    }

    @Override
    public SeamReportInstance loadReportInstance(InputStream input) throws SeamReportException {
        return null;
    }

}
