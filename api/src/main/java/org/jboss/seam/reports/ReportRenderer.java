package org.jboss.seam.reports;

import java.io.OutputStream;

public interface ReportRenderer {
    void render(ReportInstance report, OutputStream output) throws ReportException;
}
