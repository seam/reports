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

import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.openoffice.framework.OdfToolkitFacade;
import org.jboss.seam.reports.openoffice.framework.contenthandler.OODefaultListIterator;
import org.jboss.seam.reports.openoffice.model.User;
import org.jboss.seam.reports.output.PDF;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.odftoolkit.simple.TextDocument;
import org.testng.annotations.Test;

public class OOReportsListTest extends Arquillian {

    @Inject
    @Resource("listTemplate.odf")
    InputStream input;

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

    private List<User> createUserList() {
        List<User> result = new ArrayList<User>();
        result.add(new User("Alberto", "Gori"));
        result.add(new User("James", "Lee"));
        result.add(new User("Jane", "Pitt"));
        return result;
    }

    @Test
    public void fill() throws Exception {
        Report report = reportLoader.loadReport(input);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", "Alberto Gori");

        OdfToolkitFacade document = (OdfToolkitFacade) report.getDelegate();
        OOSeamReportDataSource ds = new OOSeamReportDataSource();
        ds.add(new OODefaultListIterator<User>("list", createUserList()));

        ReportDefinition reportDefinition = report.getReportDefinition();
        reportDefinition.fill(ds, parameters);
        FileOutputStream fos = new FileOutputStream("target/output.odf");
        renderer.render(report, fos);
        fos.close();

        TextDocument tester = TextDocument.loadDocument("target/output.odf");
        assertTrue(tester.getContentRoot().toString().contains("Alberto Gori"));
    }

    @Test
    public void hide() throws Exception {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", "Alberto Gori");

        Report report = reportLoader.loadReport(input);
        OdfToolkitFacade document = (OdfToolkitFacade) report.getDelegate();

        OOSeamReportDataSource ds = new OOSeamReportDataSource();
        ds.add(new OODefaultListIterator<User>("list", createUserList()).hide());
        ReportDefinition reportDefinition = report.getReportDefinition();
        reportDefinition.fill(ds, parameters);

        FileOutputStream fos = new FileOutputStream("target/hidden-list-output.odf");
        renderer.render(report, fos);
        fos.close();

    }

}
