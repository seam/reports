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
package org.jboss.seam.reports.jasperreports.qualifiers.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportDataSource;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.jasper.Jasper;
import org.jboss.seam.reports.jasper.JasperSeamReportLoader;
import org.jboss.seam.reports.output.PDF;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.oio.jpdfunit.DocumentTester;

@RunWith(Arquillian.class)
public class JasperReportsQualifierTest
{

   @Inject
   @SalesReport
   ReportDefinition<ReportDataSource, Report> salesReport;

   @Inject
   @SalesReport
   Map<String, Object> reportParams;

   @Inject
   @SalesReport
   @Resource("XlsDataSource.data.xls")
   ReportDataSource dataSource;

   @Inject
   @Jasper
   @PDF
   ReportRenderer<Report> pdfRenderer;

   @Deployment
   public static JavaArchive createArchive()
   {
      return ShrinkWrap.create(JavaArchive.class).addPackages(true, "org.jboss.seam.solder")
               .addPackages(true, "org.jboss.seam.reports").addPackages(true, "org.jboss.seam.reports.annotations")
               .addPackages(true, "org.jboss.seam.reports.jasper").addClass(JasperSeamReportLoader.class)
               .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
   }

   /**
    * Lifecycle is Compile, populate, render
    * 
    * @throws Exception
    */
   @Test
   public void testReportLifecycle() throws Exception
   {
      Report reportInstance = salesReport.fill(dataSource, reportParams);

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
}