package org.jboss.seam.reports;

import java.util.Map;

/**
 * A compiled report object. May be interpreted as a template object.
 * 
 * This object is normally created using a {@link ReportLoader}.
 * 
 * Produces {@link ReportInstance} objects by filling them with a {@link ReportDataSource} object and some optional
 * parameters
 * 
 * @author George Gastaldi
 * 
 */
public interface Report<T extends ReportDataSource, I extends ReportInstance> {

    I fill(T dataSource, Map<String, Object> parameters) throws ReportException;
}
