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

import de.oio.jpdfunit.DocumentTester;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.output.PDF;
import org.jboss.seam.reports.pentaho.PentahoReporting;
import org.jboss.seam.reports.pentaho.PentahoReportingExtension;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Test Pentaho Reporting functionality
 *
 * @author Jordan Ganoff
 */
@RunWith(Arquillian.class)
public class PentahoReportingTest {

    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.jboss.seam.solder")
                .addPackages(true, "org.jboss.seam.reports.annotation")
                .addPackages(true, "org.jboss.seam.reports.pentaho")
                .addAsServiceProvider(Extension.class, PentahoReportingExtension.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
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

    @Test
    public void loadReport_inputStream() {
        Report report = loader.loadReport(sourceReport);
        assertNotNull(report);
    }

    @Test
    public void renderReport() throws ReportException, IOException {
        Report report = loader.loadReport(sourceReport);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pdfRenderer.render(report, baos);
        assertTrue("Report is empty", baos.size() > 0);
        DocumentTester tester = new DocumentTester(new ByteArrayInputStream(baos.toByteArray()));
        try {
            tester.assertPageCountEquals(1);
        } finally {
            tester.close();
        }
    }

    @Test
    public void loadReport_name() {
        try {
            Report report = loader.loadReport("pentaho-simple.prpt");
            fail("Not expecting loadReport(String) to work properly");
        } catch (UnsupportedOperationException ex) {
        } catch (Throwable t) {
            t.printStackTrace();
            fail("Unexpected error: " + t.getMessage());
        }
    }
}
