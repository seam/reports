/**
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.reports.jasperreports.qualifiers.test;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import net.sf.jasperreports.engine.data.JRXlsDataSource;

import org.jboss.logging.Logger;
import org.jboss.seam.reports.ReportCompiler;
import org.jboss.seam.reports.ReportDataSource;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.jasper.Jasper;
import org.jboss.seam.reports.jasper.JasperSeamReportDataSource;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.seam.solder.resourceLoader.ResourceLoaderManager;

@SuppressWarnings("rawtypes")
public class JasperReportsQualifierProducer {

    @Inject
    Logger logger;

    @Produces
    @SalesReport
    ReportDefinition produceSalesReport(@Jasper ReportCompiler compiler, ResourceLoaderManager resourceLoader,
            InjectionPoint ip) {
        logger.info("Producing Sales report");
        Resource resource = getResource(ip);
        return compiler.compile(resourceLoader.getResourceAsStream(resource.value()));
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
