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
package org.jboss.seam.reports.xdocreport.renderer;

import static org.jboss.solder.reflection.AnnotationInspector.getAnnotation;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.exceptions.ReportException;
import org.jboss.seam.reports.xdocreport.XDocReportSeamReport;
import org.jboss.seam.reports.xdocreport.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.IContext;

public abstract class AbstractXDocReportRenderer implements ReportRenderer {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    InjectionPoint injectionPoint;

    @Inject
    BeanManager beanManager;

    private String via;

    @PostConstruct
    protected void init() {
        Via convertVia = getConvertVia();
        if (convertVia != null) {
            via = convertVia.value();
        }
    }

    @Override
    public final void render(Report report, OutputStream output) throws IOException {
        XDocReportSeamReport seamReport = (XDocReportSeamReport) report;
        IXDocReport delegate = seamReport.getDelegate();
        IContext context = seamReport.getContext();
        Options options = Options.getFrom(delegate.getKind()).to("PDF");
        if (via != null) {
            options.via(via);
        }
        if (log.isDebugEnabled()) {
            log.debug("Using Options from={}, to={}, via={}",
                    new Object[] { options.getFrom(), options.getTo(), options.getVia() });
        }
        try {
            delegate.convert(context, options, output);
        } catch (XDocReportException e) {
            throw new ReportException(e);
        }
    }

    protected abstract String getOutput();

    protected Via getConvertVia() {
        return getAnnotation(injectionPoint.getAnnotated(), Via.class, beanManager);
    }

}
