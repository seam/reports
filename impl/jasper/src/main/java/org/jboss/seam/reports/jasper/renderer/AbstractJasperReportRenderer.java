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
package org.jboss.seam.reports.jasper.renderer;

import java.io.IOException;
import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;

import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.exceptions.ReportException;
import org.jboss.seam.reports.jasper.JasperSeamReport;

public abstract class AbstractJasperReportRenderer implements ReportRenderer<JasperSeamReport>
{

   @Override
   public void render(JasperSeamReport reportInstance, OutputStream output) throws IOException
   {
      JRExporter exporter = getExporter();
      exporter.setParameter(JRExporterParameter.JASPER_PRINT, reportInstance.getDelegate());
      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
      try
      {
         exporter.exportReport();
      }
      catch (JRException e)
      {
         throw new ReportException(e);
      }
   }

   /**
    * Returns the exporter
    * 
    * @return
    */
   protected abstract JRExporter getExporter();

}
