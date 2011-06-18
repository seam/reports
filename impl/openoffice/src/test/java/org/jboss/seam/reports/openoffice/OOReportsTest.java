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
package org.jboss.seam.reports.openoffice;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.output.PDF;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.odftoolkit.simple.TextDocument;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.oio.jpdfunit.DocumentTester;
import de.oio.jpdfunit.document.util.TextSearchType;

public class OOReportsTest extends Arquillian {

    @Inject
    @Resource("varTemplate.odf")
    InputStream input;

    @Inject
    @Resource("facsimile.png")
    URL backgroundImage;

    @Inject
    @OOReports
    ReportLoader reportLoader;

    @Inject
    @OOReports
    ReportRenderer renderer;

    @Inject
    @OOReports
    @PDF
    ReportRenderer pdfRenderer;

    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap.create(JavaArchive.class).addPackages(true, "org.jboss.seam.solder")
                .addPackages(true, "org.jboss.seam.config").addPackages(true, "org.jboss.seam.reports")
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
                .addAsManifestResource("seam-beans.xml", ArchivePaths.create("seam-beans.xml"));
    }

    @Test
    public void fill() throws Exception {
        Report report = reportLoader.loadReport(input);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", "Alberto Gori");
        report.getReportDefinition().fill(null, parameters);
        FileOutputStream fos = new FileOutputStream("target/output.odf");
        renderer.render(report, fos);
        fos.close();

        TextDocument tester = TextDocument.loadDocument("target/output.odf");
        Assert.assertTrue(tester.getContentRoot().toString().contains("Alberto Gori"));
        Assert.assertFalse(tester.getStylesDom().toString().contains("facsimile.png"));
    }

    @Test
    public void addBackgroundImage() throws Exception {
        OOSeamReport report = (OOSeamReport) reportLoader.loadReport(input);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", "Alberto Gori");
        report.getReportDefinition().fill(null, parameters);
        report.getDelegate().addBackgroundImage(backgroundImage.toURI(), "Pictures/facsimile.png", "image/png");
        FileOutputStream fos = new FileOutputStream("target/outputWithBackgroundImage.odf");
        renderer.render(report, fos);
        fos.close();

        TextDocument tester = TextDocument.loadDocument("target/outputWithBackgroundImage.odf");
        Assert.assertTrue(tester.getContentRoot().toString().contains("Alberto Gori"));
        Assert.assertTrue(tester.getStylesDom().toString().contains("facsimile.png"));
    }

    @Test(groups="openoffice")
    public void renderToPDF() throws Exception {
        OOSeamReport report = (OOSeamReport) reportLoader.loadReport(input);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", "Alberto Gori");
        report.getReportDefinition().fill(null, parameters);
        report.getDelegate().addBackgroundImage(backgroundImage.toURI(), "Pictures/facsimile.png", "image/png");
        FileOutputStream fos = new FileOutputStream("target/outputWithBackgroundImage.pdf");
        pdfRenderer.render(report, fos);
        fos.close();

        DocumentTester tester = new DocumentTester("target/outputWithBackgroundImage.pdf");
        tester.assertContentContainsText("Hello Alberto Gori", TextSearchType.CONTAINS);
    }


}
