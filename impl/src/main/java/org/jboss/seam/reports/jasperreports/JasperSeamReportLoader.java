package org.jboss.seam.reports.jasperreports;

import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.jboss.seam.reports.SeamReport;
import org.jboss.seam.reports.SeamReportException;
import org.jboss.seam.reports.SeamReportLoader;
import org.jboss.seam.reports.SeamReportPrint;
import org.jboss.seam.reports.annotations.JasperReports;

@JasperReports
public class JasperSeamReportLoader implements SeamReportLoader {

    @Override
    public SeamReport compile(InputStream input) throws SeamReportException {
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(input);
            return new JasperSeamReport(compiledReport);
        } catch (JRException e) {
            throw new SeamReportException(e);
        }
    }

    @Override
    public SeamReport loadReport(InputStream input) throws SeamReportException {
        try {
            JasperReport report = (JasperReport)JRLoader.loadObject(input);
            return new JasperSeamReport(report);
        } catch (JRException e) {
            throw new SeamReportException(e);
        }
    }

    @Override
    public SeamReportPrint loadPrint(InputStream input) throws SeamReportException {
        try {
            JasperPrint print = (JasperPrint)JRLoader.loadObject(input);
            return new JasperSeamReportPrint(print);
        } catch (JRException e) {
            throw new SeamReportException(e);
        }
    }
}