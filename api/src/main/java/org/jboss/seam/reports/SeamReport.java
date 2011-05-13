package org.jboss.seam.reports;

import java.util.Map;

public interface SeamReport {
    SeamReportPrint fill(SeamReportDataSource dataSource) throws SeamReportException;

    SeamReportPrint fill(SeamReportDataSource dataSource, Map<String, Object> parameters) throws SeamReportException;
}
