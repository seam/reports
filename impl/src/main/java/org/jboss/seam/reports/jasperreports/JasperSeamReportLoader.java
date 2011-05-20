package org.jboss.seam.reports.jasperreports;

import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.ReportInstance;

@JasperReports
public class JasperSeamReportLoader implements ReportLoader {

    @Override
    public Report compile(InputStream input) throws ReportException {
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(input);
            return new JasperSeamReport(compiledReport);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public Report loadReport(InputStream input) throws ReportException {
        try {
            JasperReport report = (JasperReport)JRLoader.loadObject(input);
            return new JasperSeamReport(report);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public ReportInstance loadReportInstance(InputStream input) throws ReportException {
        try {
            JasperPrint print = (JasperPrint)JRLoader.loadObject(input);
            return new JasperSeamReportInstance(print);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public Report compile(String name) throws ReportException {
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(name);
            return new JasperSeamReport(compiledReport);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public Report loadReport(String name) throws ReportException {
        try {
            JasperReport report = (JasperReport)JRLoader.loadObject(name);
            return new JasperSeamReport(report);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public ReportInstance loadReportInstance(String name) throws ReportException {
        try {
            JasperPrint print = (JasperPrint)JRLoader.loadObject(name);
            return new JasperSeamReportInstance(print);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }
}