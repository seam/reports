package org.jboss.seam.reports;

import java.io.InputStream;
import java.util.Map;

public interface SeamReportCompiler {
    SeamReport compile(InputStream input) throws SeamReportException;

    SeamReport compile(InputStream input, Map<String, Object> parameters) throws SeamReportException;
}
