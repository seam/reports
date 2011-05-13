package org.jboss.seam.reports;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface ReportCompiler {
    Report compile(InputStream input, Map<String, Object> parameters) throws IOException;
}
