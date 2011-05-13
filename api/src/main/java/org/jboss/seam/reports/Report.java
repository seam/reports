package org.jboss.seam.reports;

import java.util.Map;

public interface Report {
    ReportPrint fill(ReportDataSource dataSource, Map<String, Object> parameters) throws ReportException;
}
