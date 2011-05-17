package org.jboss.seam.reports.jasperreports.qualifiers.test;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import net.sf.jasperreports.engine.data.JRXlsDataSource;

import org.jboss.logging.Logger;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportDataSource;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.annotations.frameworks.JasperReports;
import org.jboss.seam.reports.jasperreports.JasperSeamReportDataSource;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.seam.solder.resourceLoader.ResourceLoaderManager;

public class JasperReportsQualifierProducer {

    @Inject
    Logger logger;

    @Produces
    @SalesReport
    Report produceSalesReport(@JasperReports Instance<ReportLoader> loader, ResourceLoaderManager resourceLoader,
            InjectionPoint ip) {
        logger.info("Producing Sales report");
        Resource resource = getResource(ip);
        Report report = null;
        report = loader.get().compile(resourceLoader.getResourceAsStream(resource.value()));
        return report;
    }

    @Produces
    @SalesReport
    @Resource("")
    ReportDataSource getDataSource(InjectionPoint ip, ResourceLoaderManager loaderManager) throws Exception {
        JRXlsDataSource ds;
        String[] columnNames = new String[] { "city", "id", "name", "address", "state" };
        int[] columnIndexes = new int[] { 0, 2, 3, 4, 5 };
        Resource resource = getResource(ip);
        ds = new JRXlsDataSource(loaderManager.getResourceAsStream(resource.value()));
        ds.setColumnNames(columnNames, columnIndexes);
        return new JasperSeamReportDataSource(ds);
    }

    @Produces
    @SalesReport
    Map<String, Object> producesParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        // Preparing parameters
        params.put("ReportTitle", "Address Report");
        params.put("DataFile", "XlsDataSource.data.xls - XLS data source");
        Set<String> states = new HashSet<String>();
        states.add("Active");
        states.add("Trial");
        params.put("IncludedStates", states);
        return params;
    }
    
    
    
    /**
     * Retrieves the {@link Resource} annotation
     * 
     * @param ip
     * @return
     */
    private Resource getResource(InjectionPoint ip) {
        Resource resource = null;
        Set<Annotation> qualifiers = ip.getQualifiers();
        for (Annotation an : qualifiers) {
            Class<? extends Annotation> annotationType = an.annotationType();
            if (annotationType == Resource.class) {
                resource = Resource.class.cast(an);
                break;
            }
        }
        if (resource == null) {
            for (Annotation an : qualifiers) {
                Class<? extends Annotation> annotationType = an.annotationType();
                if (annotationType.isAnnotationPresent(Resource.class)) {
                    resource = annotationType.getAnnotation(Resource.class);
                    break;
                }
            }
        }
        return resource;
    }
}
