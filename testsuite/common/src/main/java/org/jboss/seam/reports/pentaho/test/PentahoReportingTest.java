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
package org.jboss.seam.reports.pentaho.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.exceptions.ReportException;
import org.jboss.seam.reports.output.CSV;
import org.jboss.seam.reports.output.PDF;
import org.jboss.seam.reports.output.XML;
import org.jboss.seam.reports.pentaho.annotations.PentahoReporting;
import org.jboss.seam.reports.test.Utils;
import org.jboss.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.oio.jpdfunit.DocumentTester;

/**
 * Test Pentaho Reporting functionality
 * 
 * @author Jordan Ganoff
 */
@RunWith(Arquillian.class)
public class PentahoReportingTest
{
   @Deployment(name="PentahoReporting")
   public static WebArchive createArchive()
   {
      return Utils.getDeploymentFactory().pentahoDeployment() 
               .addClass(PentahoReportingTest.class)
               .addAsResource("pentaho-simple.prpt", "pentaho-simple.prpt");              
   }

   @Inject
   @Resource("pentaho-simple.prpt")
   private InputStream sourceReport;

   @Inject
   @PentahoReporting
   private ReportLoader loader;

   @Inject
   @PentahoReporting
   @PDF
   private ReportRenderer<Report> pdfRenderer;

   @Inject
   @PentahoReporting
   @XML
   private ReportRenderer<Report> xmlRenderer;

   @Inject
   @PentahoReporting
   @CSV
   private ReportRenderer<Report> csvRenderer;

   @Test
   public void loadReport_inputStream()
   {
      Report report = loader.loadReport(sourceReport);
      assertNotNull(report);
   }

   @Test
   public void renderReport() throws ReportException, IOException
   {
      Report report = loader.loadReport(sourceReport);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      pdfRenderer.render(report, baos);
      assertTrue("Report is empty", baos.size() > 0);
      DocumentTester tester = new DocumentTester(new ByteArrayInputStream(baos.toByteArray()));
      try
      {
         tester.assertPageCountEquals(1);
      }
      finally
      {
         tester.close();
      }
   }

   @Test
   public void renderReportAsXML() throws ReportException, IOException
   {
      Report report = loader.loadReport(sourceReport);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      xmlRenderer.render(report, baos);
      assertTrue("Report is empty", baos.size() > 0);
   }

   @Test
   public void renderReportAsCSV() throws ReportException, IOException
   {
      Report report = loader.loadReport(sourceReport);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      csvRenderer.render(report, baos);
      assertTrue("Report is empty", baos.size() > 0);
   }

   @Test
   public void loadReport_name(@Resource("pentaho-simple.prpt") URL sourceReport)
   {
      Report report = loader.loadReport(sourceReport.toExternalForm());
      assertNotNull(report);
   }
}
