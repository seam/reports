package org.jboss.seam.reports;

import java.io.InputStream;

/**
 * Loads a report from a {@link InputStream}
 * 
 * The report may required to be compiled first. In these cases, the compile method should be invoked then.
 * 
 * @author george
 * 
 */
public interface ReportLoader {
    Report<?,?> compile(InputStream input) throws ReportException;
    Report<?,?> compile(String name) throws ReportException;

    Report<?,?> loadReport(InputStream input) throws ReportException;
    Report<?,?> loadReport(String name) throws ReportException;

    ReportInstance loadReportInstance(InputStream input) throws ReportException;
    ReportInstance loadReportInstance(String name) throws ReportException;
}
