package org.jboss.seam.reports.jasperreports;

import java.io.InputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import org.jboss.seam.reports.SeamReport;
import org.jboss.seam.reports.SeamReportCompiler;
import org.jboss.seam.reports.SeamReportException;

public class JasperSeamReportCompiler implements SeamReportCompiler {

    @Override
    public SeamReport compile(final InputStream input, Map<String, Object> params) throws SeamReportException {
        return compile(input);
    }

    @Override
    public SeamReport compile(InputStream input) throws SeamReportException {
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(input);
            return new JasperSeamReport(compiledReport);
        } catch (JRException e) {
            throw new SeamReportException(e);
        }
    }

}
