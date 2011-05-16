package org.jboss.seam.reports;

import java.io.OutputStream;

public interface SeamReportInstance {

    boolean isOutputTypeSupported(SeamReportOutputType type);

    void render(SeamReportOutputType type, OutputStream output) throws SeamReportException;
}
