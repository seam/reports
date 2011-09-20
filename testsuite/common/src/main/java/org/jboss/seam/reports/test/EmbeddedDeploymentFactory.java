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
package org.jboss.seam.reports.test;

import javax.enterprise.inject.spi.Extension;

import org.jboss.seam.reports.jasper.JasperSeamReportLoader;
import org.jboss.seam.reports.pentaho.PentahoReportingExtension;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * Deployment factory for embedded environments, expecting to find dependencies on classpath.
 * 
 * @author maschmid
 *
 */
public class EmbeddedDeploymentFactory implements DeploymentFactory {
 
    @Override
    public WebArchive jasperDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addPackages(true, "org.jboss.seam.solder")
            .addPackages(true, "org.jboss.seam.reports").addPackages(true, "org.jboss.seam.reports.annotations")
            .addPackages(true, "org.jboss.seam.reports.jasper").addClass(JasperSeamReportLoader.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Override
    public WebArchive mvelDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addPackages(true, "org.jboss.seam.solder")
            .addPackages(true, "org.jboss.seam.reports.annotation")
            .addPackages(true, "org.jboss.seam.reports.mvel")
            .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Override
    public WebArchive pentahoDeployment() {
        return ShrinkWrap.create(WebArchive.class).addPackages(true, "org.jboss.seam.solder")
            .addPackages(true, "org.jboss.seam.reports.annotation")
            .addPackages(true, "org.jboss.seam.reports.pentaho")
            .addAsServiceProvider(Extension.class, PentahoReportingExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Override
    public WebArchive xdocreportDeployment() {
        return ShrinkWrap.create(WebArchive.class).addPackages(true, "org.jboss.seam.solder")
            .addPackages(true, "org.jboss.seam.reports.annotation")
            .addPackages(true, "org.jboss.seam.reports.xdocreport")
            .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

}
