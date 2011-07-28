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

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.exceptions.ReportException;
import org.jboss.seam.solder.resourceLoader.ResourceLoaderManager;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

@XDocReport
public class XDocReportSeamReportLoader implements ReportLoader
{

   @Inject
   private ResourceLoaderManager resourceLoaderManager;

   @Override
   public XDocReportSeamReport loadReport(InputStream input) throws ReportException
   {
      IXDocReport report;
      try
      {
         // TODO: Check how to do it with freemarker
         report = XDocReportRegistry.getRegistry().loadReport(input, TemplateEngineKind.Velocity);
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         throw new ReportException(e);
      }
      catch (XDocReportException e)
      {
         throw new ReportException(e);
      }
      return new XDocReportSeamReport(report);
   }

   @Override
   public XDocReportSeamReport loadReportDefinition(InputStream input) throws ReportException
   {
      return loadReport(input);
   }

   @Override
   public XDocReportSeamReport loadReportDefinition(String name) throws ReportException
   {
      return loadReport(name);
   }

   @Override
   public XDocReportSeamReport loadReport(String name) throws ReportException
   {
      InputStream resourceAsStream = resourceLoaderManager.getResourceAsStream(name);
      return loadReport(resourceAsStream);
   }
}

