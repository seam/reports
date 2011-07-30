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
package org.jboss.seam.reports.xdocreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.exceptions.IllegalReportDataSourceException;
import org.jboss.seam.reports.exceptions.ReportException;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.io.XDocArchive;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class XDocReportSeamReportDefinition implements ReportDefinition
{

   private XDocArchive archive;
   private TemplateEngineKind engineKind;

   public XDocReportSeamReportDefinition(XDocArchive archive, TemplateEngineKind engineKind)
   {
      this.archive = archive;
      this.engineKind = engineKind;
   }

   @SuppressWarnings("unchecked")
   @Override
   public XDocReportSeamReport fill(Object dataSource, Map<String, Object> parameters) throws ReportException
   {
      IContext context;
      IXDocReport report;
      try
      {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         XDocArchive.writeZip(archive, baos);
         // From XDocReportSeamReportLoader
         report = XDocReportRegistry.getRegistry().loadReport(new ByteArrayInputStream(baos.toByteArray()), engineKind);
         if (dataSource == null)
         {
            if (parameters == null)
            {
               throw new IllegalReportDataSourceException("No Datasource provided");
            }
            context = report.createContext();
            fillReportFromMap(context, parameters);
         }
         else if (dataSource instanceof Map)
         {
            context = report.createContext();
            fillReportFromMap(context, (Map<String, Object>) dataSource);
         }
         else
         {
            try
            {
               context = IContext.class.cast(dataSource);
            }
            catch (ClassCastException cce)
            {
               throw new IllegalReportDataSourceException();
            }
         }
         return new XDocReportSeamReport(this, report, context);
      }
      catch (IOException e)
      {
         throw new ReportException(e);
      }
      catch (XDocReportException e)
      {
         throw new ReportException(e);
      }
   }

   protected void fillReportFromMap(IContext context, Map<String, Object> params)
   {
      for (Entry<String, Object> entry : params.entrySet())
      {
         context.put(entry.getKey(), entry.getValue());
      }
   }

}
