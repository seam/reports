package org.jboss.seam.reports;

import java.io.OutputStream;

public interface SeamReportPrint {

    boolean isOutputTypeSupported(SeamReportOutputType type);

    void outputTo(SeamReportOutputType type, OutputStream output) throws SeamReportException;
}
