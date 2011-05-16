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
public interface SeamReportLoader {
    SeamReport compile(InputStream input) throws SeamReportException;
    SeamReport compile(String name) throws SeamReportException;

    SeamReport loadReport(InputStream input) throws SeamReportException;
    SeamReport loadReport(String name) throws SeamReportException;

    SeamReportInstance loadReportInstance(InputStream input) throws SeamReportException;
    SeamReportInstance loadReportInstance(String name) throws SeamReportException;
}
