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

import org.jboss.seam.reports.Report;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.IContext;

public class XDocReportSeamReport implements Report
{

   private static final long serialVersionUID = 1L;
   private IXDocReport xdocReport;
   private IContext context;
   private XDocReportSeamReportDefinition reportDefinition;

   public XDocReportSeamReport(XDocReportSeamReportDefinition definition, IXDocReport report, IContext context)
   {
      this.reportDefinition = definition;
      this.xdocReport = report;
      this.context = context;
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

   @Override
   public XDocReportSeamReportDefinition getReportDefinition()
   {
      return reportDefinition;
   }
}
