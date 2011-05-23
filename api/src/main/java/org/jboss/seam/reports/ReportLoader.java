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

    // TODO: Should these compile methods be moved as it doesn´t make sense to some reporting implementations ?
    Report<? extends ReportDataSource, ? extends ReportInstance> compile(InputStream input) throws ReportException;

    Report<? extends ReportDataSource, ? extends ReportInstance> compile(String name) throws ReportException;

    Report<? extends ReportDataSource, ? extends ReportInstance> loadReport(InputStream input) throws ReportException;

    Report<? extends ReportDataSource, ? extends ReportInstance> loadReport(String name) throws ReportException;

    ReportInstance loadReportInstance(InputStream input) throws ReportException;

    ReportInstance loadReportInstance(String name) throws ReportException;
}
