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

import java.util.Map;
import java.util.Map.Entry;

import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.exceptions.IllegalReportDataSourceException;
import org.jboss.seam.reports.exceptions.ReportException;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.IContext;

@XDocReport
public class XDocReportSeamReport implements ReportDefinition<XDocReportSeamReport>, Report
{

   private static final long serialVersionUID = 1L;
   private IXDocReport xdocReport;
   private IContext context;

   public XDocReportSeamReport(IXDocReport report)
   {
      this.xdocReport = report;
   }

   @SuppressWarnings("unchecked")
   @Override
   public XDocReportSeamReport fill(Object dataSource, Map<String, Object> parameters)
            throws ReportException
   {
      if (dataSource == null)
      {
         if (parameters == null)
         {
            throw new IllegalReportDataSourceException("No Datasource provided");
         }
         fillReportFromMap(parameters);
      }
      else if (dataSource instanceof Map)
      {
         fillReportFromMap((Map<String, Object>) dataSource);
      }
      else
      {
         try
         {
            this.context = IContext.class.cast(dataSource);
         }
         catch (ClassCastException cce)
         {
            throw new IllegalReportDataSourceException();
         }
      }
      return this;
   }

   protected void fillReportFromMap(Map<String, Object> params) throws ReportException
   {
      try
      {
         this.context = xdocReport.createContext();
      }
      catch (XDocReportException e)
      {
         throw new ReportException(e);
      }
      for (Entry<String, Object> entry : params.entrySet())
      {
         this.context.put(entry.getKey(), entry.getValue());
      }
   }

   @Override
   public IXDocReport getDelegate()
   {
      return xdocReport;
   }

   public IContext getContext()
   {
      return context;
   }

   /**
    * It´s the same reference
    */
   @Override
   public XDocReportSeamReport getReportDefinition()
   {
      return this;
   }
}
