/*
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
package org.jboss.seam.reports.pentaho.renderer;

import static org.jboss.seam.solder.reflection.AnnotationInspector.getAnnotation;

import java.io.IOException;
import java.io.OutputStream;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.pentaho.PentahoReporting;
import org.jboss.seam.reports.pentaho.PentahoSeamReport;
import org.jboss.seam.reports.spi.ReportOutputBinding;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.csv.CSVReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xml.XmlTableReportUtil;

/**
 * Pentaho Reporting Engine Report Renderer
 * 
 * @author Jordan Ganoff
 */
@PentahoReporting
public class PentahoReportingRenderer implements ReportRenderer<PentahoSeamReport>
{
   @Inject
   private InjectionPoint ip;

   @Inject
   private BeanManager bm;

   @Override
   public void render(PentahoSeamReport report, OutputStream output) throws ReportException
   {
      ReportOutputBinding an = getAnnotation(ip.getAnnotated(), ReportOutputBinding.class, bm);
      MasterReport mr = report.getDelegate();
      try
      {
         if ("PDF".equals(an.value()))
         {
            PdfReportUtil.createPDF(mr, output);
         }
         else if ("XLS".equals(an.value()))
         {
            ExcelReportUtil.createXLS(mr, output);
         }
         else if ("CSV".equals(an.value()))
         {
            CSVReportUtil.createCSV(mr, output, "UTF-8");
         }
         else if ("XML".equals(an.value()))
         {
            XmlTableReportUtil.createStreamXML(mr, output);
         }
         else
         {
            throw new ReportException("Unknown output format: " + an);
         }
      }
      catch (ReportProcessingException ex)
      {
         throw new ReportException("Error rendering report", ex);
      }
      catch (IOException io)
      {
         throw new ReportException("Error rendering report", io);
      }
   }
}
