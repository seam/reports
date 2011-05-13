package org.jboss.seam.reports;

import java.util.Map;

/**
 * A compiled report object. May be viewed as a template object.
 * 
 * This object are normally created using a {@link SeamReportLoader}.
 * 
 * Produces {@link SeamReportPrint} objects by filling them with a {@link SeamReportDataSource} object and some optional
 * parameters
 * 
 * @author george
 * 
 */
public interface SeamReport {

    SeamReportPrint fill(SeamReportDataSource dataSource) throws SeamReportException;

    SeamReportPrint fill(SeamReportDataSource dataSource, Map<String, Object> parameters) throws SeamReportException;
}
