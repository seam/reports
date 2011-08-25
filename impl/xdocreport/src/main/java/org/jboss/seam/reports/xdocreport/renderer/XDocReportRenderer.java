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

import static org.jboss.seam.solder.reflection.AnnotationInspector.getAnnotation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.exceptions.ReportException;
import org.jboss.seam.reports.exceptions.UnsupportedReportOutputException;
import org.jboss.seam.reports.spi.ReportOutputBinding;
import org.jboss.seam.reports.xdocreport.XDocReportSeamReport;
import org.jboss.seam.reports.xdocreport.annotations.Via;
import org.jboss.seam.reports.xdocreport.annotations.XDocReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.IContext;

@XDocReport
public class XDocReportRenderer implements ReportRenderer<XDocReportSeamReport>
{

   private static Logger log = LoggerFactory.getLogger(XDocReportRenderer.class);

   @Inject
   InjectionPoint injectionPoint;

   @Inject
   BeanManager beanManager;

   @Override
   public void render(XDocReportSeamReport report, OutputStream output) throws IOException
   {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      IXDocReport delegate = report.getDelegate();
      IContext context = report.getContext();
      try
      {
         delegate.process(context, baos);
      }
      catch (XDocReportException e)
      {
         throw new ReportException(e);
      }
      // Do Convert if a ReportOutputBinding was specified
      ReportOutputBinding reportOutputBinding = getReportOutputBinding();
      if (reportOutputBinding != null)
      {
         Options options = Options.getFrom(delegate.getKind()).to(reportOutputBinding.value());
         Via via = getConvertVia();
         if (via != null)
         {
            options.via(via.value());
         }
         log.debug("Using Options from={}, to={}, via={}",
                  new Object[] { options.getFrom(), options.getTo(), options.getVia() });
         IConverter converter = ConverterRegistry.getRegistry().getConverter(options);
         if (converter == null)
         {
            throw new UnsupportedReportOutputException("No converter found for this injection point: " + injectionPoint);
         }
         try
         {
            converter.convert(new ByteArrayInputStream(baos.toByteArray()), output, options);
         }
         catch (XDocConverterException e)
         {
            throw new ReportException(e);
         }
      }
      else
      {
         output.write(baos.toByteArray());
      }
   }

   protected ReportOutputBinding getReportOutputBinding()
   {
      return getAnnotation(injectionPoint.getAnnotated(), ReportOutputBinding.class, beanManager);
   }

   protected Via getConvertVia()
   {
      return getAnnotation(injectionPoint.getAnnotated(), Via.class, beanManager);
   }

}
