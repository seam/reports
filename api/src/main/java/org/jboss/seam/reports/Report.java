package org.jboss.seam.reports;

import java.util.Map;

/**
 * A compiled report object. May be viewed as a template object.
 * 
 * This object are normally created using a {@link ReportLoader}.
 * 
 * Produces {@link ReportInstance} objects by filling them with a {@link ReportDataSource} object and some optional
 * parameters
 * 
 * @author george
 * 
 */
public interface Report {

    ReportInstance fill(ReportDataSource dataSource, Map<String, Object> parameters) throws ReportException;
}
