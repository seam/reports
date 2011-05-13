package org.jboss.seam.reports;

import java.util.Map;

/**
 * A compiled report object. May be viewed as a template object.
 * 
 * This object are normally created using a {@link SeamReportLoader}.
 * 
 * Produces {@link SeamReportInstance} objects by filling them with a {@link SeamReportDataSource} object and some optional
 * parameters
 * 
 * @author george
 * 
 */
public interface SeamReport {

    SeamReportInstance fill(SeamReportDataSource dataSource, Map<String, Object> parameters) throws SeamReportException;
}
