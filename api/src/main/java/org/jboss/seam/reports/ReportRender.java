package org.jboss.seam.reports;

import java.io.IOException;
import java.io.OutputStream;

public interface ReportRender {

    public void outputReport(ReportPrint print, OutputStream output) throws IOException;
}
