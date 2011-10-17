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
package org.jboss.seam.reports.jasperreports.test;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRXlsDataSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportCompiler;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.jasper.annotations.Jasper;
import org.jboss.seam.reports.output.CSV;
import org.jboss.seam.reports.output.PDF;
import org.jboss.seam.reports.output.XML;
import org.jboss.seam.reports.test.Utils;
import org.jboss.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.oio.jpdfunit.DocumentTester;

@RunWith(Arquillian.class)
public class JasperReportsTest
{
   @Inject
   @Resource("XlsDataSourceReport.jrxml")
   InputStream sourceReport;

   @Inject
   @Jasper
   ReportCompiler compiler;

   @Inject
   @Jasper
   JRDataSource jasperDataSource;

   @Inject
   @Jasper
   @PDF
   ReportRenderer<Report> pdfRenderer;

   @Inject
   @XML
   @Jasper
   ReportRenderer<Report> xmlRenderer;

   @Inject
   @CSV
   @Jasper
   ReportRenderer<Report> csvRenderer;

   @Deployment(name="JasperReports")
   public static WebArchive createArchive()
   {
      return Utils.getDeploymentFactory().jasperDeployment()
               .addClasses(JasperReportsTest.class, JasperReportsProducer.class)               
               .addAsResource("XlsDataSource.data.xls", "XlsDataSource.data.xls")
               .addAsResource("XlsDataSourceReport.jrxml", "XlsDataSourceReport.jrxml");
   }

   @Test
   public void testCompilerNotNull() throws Exception
   {
      assertNotNull(compiler);
   }

   @Test
   public void testReportLifecycle() throws Exception
   {
      // source
      ReportDefinition report = compiler.compile(sourceReport);

      Map<String, Object> params = new HashMap<String, Object>();
      // Preparing parameters
      params.put("ReportTitle", "Address Report");
      params.put("DataFile", "XlsDataSource.data.xls - XLS data source");
      Set<String> states = new HashSet<String>();
      states.add("Active");
      states.add("Trial");
      params.put("IncludedStates", states);

      Report reportInstance = report.fill(jasperDataSource, params);

      ByteArrayOutputStream os = new ByteArrayOutputStream(); // OutputStream
      // Render output as the desired content
      pdfRenderer.render(reportInstance, os);
      DocumentTester tester = new DocumentTester(new ByteArrayInputStream(os.toByteArray()));
      try
      {
         tester.assertPageCountEquals(2);
      }
      finally
      {
         tester.close();
      }
   }

   @Test
   public void testReportXML() throws Exception
   {
      // source
      ReportDefinition report = compiler.compile(sourceReport);

      Map<String, Object> params = new HashMap<String, Object>();
      // Preparing parameters
      params.put("ReportTitle", "Address Report");
      params.put("DataFile", "XlsDataSource.data.xls - XLS data source");
      Set<String> states = new HashSet<String>();
      states.add("Active");
      states.add("Trial");
      params.put("IncludedStates", states);

      Report reportInstance = report.fill(jasperDataSource, params);

      ByteArrayOutputStream os = new ByteArrayOutputStream(); // OutputStream
      // Render output as the desired content

      xmlRenderer.render(reportInstance, os);
      assertThat(os.size(), not(0));
   }

   @Test
   public void testReportCSV() throws Exception
   {
      // source
      ReportDefinition report = compiler.compile(sourceReport);

      Map<String, Object> params = new HashMap<String, Object>();
      // Preparing parameters
      params.put("ReportTitle", "Address Report");
      params.put("DataFile", "XlsDataSource.data.xls - XLS data source");
      Set<String> states = new HashSet<String>();
      states.add("Active");
      states.add("Trial");
      params.put("IncludedStates", states);

      Report reportInstance = report.fill(jasperDataSource, params);

      ByteArrayOutputStream os = new ByteArrayOutputStream(); // OutputStream
      // Render output as the desired content

      csvRenderer.render(reportInstance, os);
      assertThat(os.size(), not(0));
   }
   
   public static class JasperReportsProducer
   {
      @Produces
      @Jasper
      JRDataSource getDataSource(@Resource("XlsDataSource.data.xls") InputStream dataSource) throws Exception
      {
         JRXlsDataSource ds;
         String[] columnNames = new String[] { "city", "id", "name", "address", "state" };
         int[] columnIndexes = new int[] { 0, 2, 3, 4, 5 };
         ds = new JRXlsDataSource(dataSource);
         ds.setColumnNames(columnNames, columnIndexes);
         return ds;
      }
   }
}