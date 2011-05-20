package org.jboss.seam.reports.jasperreports.renderer;

import static org.jboss.seam.solder.reflection.AnnotationInspector.getAnnotation;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import net.sf.jasperreports.engine.JRExporter;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.jasperreports.JasperReports;
import org.jboss.seam.reports.spi.ReportOutputBinding;
import org.jboss.seam.solder.reflection.Reflections;

/**
 * Dynamic renderer.
 * 
 * @author george
 * 
 */
@JasperReports
public class DynamicJasperReportRenderer extends AbstractJasperReportRenderer {

    private static final String RENDERER_PREFIX = "net.sf.jasperreports.engine.export.JR";
    private static final String RENDERER_SUFFIX = "Exporter";

    @Inject
    InjectionPoint injectionPoint;

    @Inject
    BeanManager beanManager;

    /**
     * The exporter returned based on the report output binding
     * 
     * TODO: change it to CDI creation
     */
    @Override
    protected JRExporter getExporter() {
        try {
            Class<JRExporter> exporterClass = getExporterClass();
            return exporterClass.newInstance();
        } catch (Exception e) {
            throw new ReportException("Error while creating renderer", e);
        }
    }

    @SuppressWarnings("unchecked")
    protected Class<JRExporter> getExporterClass() {
        Class<JRExporter> clazz;
        ReportOutputBinding an = getAnnotation(injectionPoint.getAnnotated(), ReportOutputBinding.class, beanManager);
        String value = an.value();
        String outputType = firstLetterCaps(value);
        String className = RENDERER_PREFIX + outputType + RENDERER_SUFFIX;
        try {
            clazz = (Class<JRExporter>) Reflections.classForName(className);
        } catch (ClassNotFoundException cnfe) {
            throw new ReportException("Class " + className + " not found", cnfe);
        }
        return clazz;
    }

    private static String firstLetterCaps(String data) {
        String firstLetter = data.substring(0, 1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }
}
