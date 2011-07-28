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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportLoader;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.parser.base.ReportGenerator;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.xml.sax.InputSource;

/**
 * Pentaho Reporting Engine Report Loader
 * 
 * @author Jordan Ganoff
 */
@SuppressWarnings("deprecation")
@PentahoReporting
public class PentahoSeamReportLoader implements ReportLoader
{
   private ResourceManager rmgr = new ResourceManager();

   @Override
   public PentahoSeamReportDefinition loadReportDefinition(InputStream input) throws ReportException
   {
      return new PentahoSeamReportDefinition(loadMasterReport(input));
   }

   @Override
   public PentahoSeamReportDefinition loadReportDefinition(String name) throws ReportException
   {
      return new PentahoSeamReportDefinition(loadMasterReport(name));
   }

   @Override
   public PentahoSeamReport loadReport(InputStream input) throws ReportException
   {
      return new PentahoSeamReport(loadMasterReport(input));
   }

   @Override
   public PentahoSeamReport loadReport(String name) throws ReportException
   {
      return new PentahoSeamReport(loadMasterReport(name));
   }

   /**
    * Parse a report from the provided input stream
    * 
    * @param input Input stream containing Pentaho Reporting report
    * @return Pentaho Reporting report
    * @throws ReportException if the input stream is null or contains a malformed report
    */
   private MasterReport loadMasterReport(InputStream input) throws ReportException
   {
      try
      {
         rmgr.registerDefaults();
         // TODO Figure out a non-deprecated way to load a report from InputStream
         final ReportGenerator generator = ReportGenerator.createInstance();
         final InputSource repDefInputSource = new InputSource(input);
         return generator.parseReport(repDefInputSource, null);
      }
      catch (Exception ex)
      {
         throw new ReportException("Unable to load report", ex);
      }
   }

   /**
    * Loads a {@link MasterReport} by name
    * 
    * @param name
    * @return
    * @throws ReportException
    */
   private MasterReport loadMasterReport(String name) throws ReportException
   {
      try
      {
         rmgr.registerDefaults();
         Resource resource = rmgr.createDirectly(new URL(name), MasterReport.class);
         return (MasterReport) resource.getResource();
      }
      catch (MalformedURLException ex)
      {
         throw new ReportException("Invalid report path: " + name, ex);
      }
      catch (Exception ex)
      {
         throw new ReportException("Error loading report with name '" + name + "'", ex);
      }
   }
}
