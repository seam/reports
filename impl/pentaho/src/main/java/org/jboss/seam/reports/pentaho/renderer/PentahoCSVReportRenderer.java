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
package org.jboss.seam.reports.pentaho.renderer;

import java.io.IOException;
import java.io.OutputStream;

import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.exceptions.ReportException;
import org.jboss.seam.reports.output.CSV;
import org.jboss.seam.reports.pentaho.annotations.PentahoReporting;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.modules.output.table.csv.CSVReportUtil;

@PentahoReporting
@CSV
public class PentahoCSVReportRenderer implements ReportRenderer
{

   @Override
   public void render(Report report, OutputStream output) throws IOException
   {
      MasterReport mr = (MasterReport) report.getDelegate();
      try
      {
         CSVReportUtil.createCSV(mr, output, "UTF-8");
      }
      catch (ReportProcessingException ex)
      {
         throw new ReportException("Error rendering report", ex);
      }
   }

}
