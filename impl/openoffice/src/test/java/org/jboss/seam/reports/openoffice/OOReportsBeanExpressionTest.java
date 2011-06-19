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
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.openoffice.model.Address;
import org.jboss.seam.reports.openoffice.model.User;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.odftoolkit.simple.TextDocument;
import org.testng.annotations.Test;

public class OOReportsBeanExpressionTest extends Arquillian {

    @Inject
    @Resource("expressionTemplate.odf")
    InputStream input;

    @Inject
    @OOReports
    ReportLoader reportLoader;

    @Inject
    @OOReports
    ReportRenderer renderer;

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
        
        User user = new User("Alberto", "Gori");
        user.setAddress(new Address("Via Roma 10, Bassano del Grappa", "Italy", "34653"));
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("user", user);
        report.getReportDefinition().fill(null, parameters);
        
        final String filename = "target/expressionTemplate_fill.odf";
        FileOutputStream fos = new FileOutputStream(filename);
        renderer.render(report, fos);
        fos.close();

        TextDocument tester = TextDocument.loadDocument(filename);
        assert tester.getContentRoot().toString().contains("Alberto");
        assert tester.getContentRoot().toString().contains("34653");
        
    }


}
