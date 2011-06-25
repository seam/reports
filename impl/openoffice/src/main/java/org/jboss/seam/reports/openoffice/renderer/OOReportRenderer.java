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
package org.jboss.seam.reports.openoffice.renderer;

import static org.jboss.seam.solder.reflection.AnnotationInspector.getAnnotation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.openoffice.OOReports;
import org.jboss.seam.reports.openoffice.OOSeamReport;
import org.jboss.seam.reports.openoffice.framework.JODConverterFacade;
import org.jboss.seam.reports.spi.ReportOutputBinding;

@OOReports
public class OOReportRenderer implements ReportRenderer<OOSeamReport> {

    @Inject
    @OOReports
    JODConverterFacade facade;

    @Inject
    InjectionPoint injectionPoint;

    @Inject
    BeanManager beanManager;

    protected ReportOutputBinding getReportOutputBinding() {
        ReportOutputBinding an = getAnnotation(injectionPoint.getAnnotated(), ReportOutputBinding.class, beanManager);
        return an;
    }

    @Override
    public void render(OOSeamReport report, OutputStream output) throws IOException {
        try {
            ReportOutputBinding binding = getReportOutputBinding();
            if (null == binding) {
                report.getDelegate().getDocument().save(output);
            } else if ("PDF".equals(binding.value())) {

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                report.getDelegate().getDocument().save(bos);
                byte[] bytes = bos.toByteArray();
                facade.convert(new ByteArrayInputStream(bytes), output);

            } else {
                throw new ReportException("output not supported: " + binding);
            }
        } catch (ReportException e) {
            throw e;
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

}
