package org.jboss.seam.reports;

import java.io.OutputStream;

public interface SeamReportRenderer {
    void render(SeamReportInstance report, OutputStream output) throws SeamReportException;
}
