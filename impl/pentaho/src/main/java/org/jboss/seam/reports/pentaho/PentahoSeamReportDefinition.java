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
package org.jboss.seam.reports.pentaho;

import java.util.Map;

import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.exceptions.ReportException;
import org.jboss.seam.reports.spi.ReportUtils;
import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;

/**
 * Pentaho Reporting Engine Seam Component
 * 
 * @author Jordan Ganoff
 */
public class PentahoSeamReportDefinition implements ReportDefinition<PentahoSeamReport>
{
   private MasterReport report;

   public PentahoSeamReportDefinition(MasterReport report)
   {
      if (report == null)
      {
         throw new NullPointerException();
      }
      this.report = report;
   }

   @Override
   public PentahoSeamReport fill(Object dataSource, Map<String, Object> parameters)
            throws ReportException
   {
      MasterReport masterReport;
      if (dataSource != null)
      {
         try
         {
            masterReport = (MasterReport) report.clone();
         }
         catch (CloneNotSupportedException ignored)
         {
            // Should not happen
            throw new IllegalStateException("Error while cloning MasterReport", ignored);
         }
         DataFactory dataFactory = ReportUtils.castDataSource(dataSource, DataFactory.class);
         masterReport.setDataFactory(dataFactory);
      }
      else
      {
         masterReport = report;
      }
      return new PentahoSeamReport(masterReport);
   }
}
