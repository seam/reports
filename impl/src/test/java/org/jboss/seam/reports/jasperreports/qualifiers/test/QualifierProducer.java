package org.jboss.seam.reports.jasperreports.qualifiers.test;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.seam.solder.resourceLoader.ResourceLoaderManager;

public class QualifierProducer {
    
    @Inject Logger logger;
    
    @Produces
    @SalesReport
    Report produceSalesReport(@Any Instance<ReportLoader> loader, BeanManager beanManager,
            ResourceLoaderManager resourceLoader, InjectionPoint ip) {
        logger.info("Producing Sales report");
        Set<Annotation> qualifiers = ip.getQualifiers();
        Report report = null;
        for (Annotation an : qualifiers) {
            Class<? extends Annotation> annotationType = an.annotationType();
            if (annotationType.isAnnotationPresent(Resource.class)) {
                report = loader.get().compile(
                        resourceLoader.getResourceAsStream(annotationType.getAnnotation(Resource.class).value()));
            }
        }
        return report;
    }

}
