package org.jboss.seam.reports;

import java.io.OutputStream;

/**
 * Renders a report on the supplied {@link OutputStream}
 * 
 * @author george
 * 
 */
public interface ReportRenderer<I extends ReportInstance> {
    void render(I report, OutputStream output) throws ReportException;
}
