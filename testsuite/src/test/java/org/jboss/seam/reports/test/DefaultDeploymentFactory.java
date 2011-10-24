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

import java.io.File;
import java.util.Collection;

import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependency;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolutionFilter;

/**
 * Deployment factory for proper containers
 * @author maschmid
 *
 */
public class DefaultDeploymentFactory implements DeploymentFactory {

    public static final String SETTINGS_XML = "../settings.xml";
    public static final String SEAM_REPORTS_API_JAR = "../api/target/seam-reports-api.jar";
    
    public static final String SEAM_REPORTS_JASPER_JAR = "../impl/jasper/target/seam-reports-jasper.jar";
    public static final String SEAM_REPORTS_JASPER_POM = "../impl/jasper/pom.xml";
    
    public static final String SEAM_REPORTS_MVEL_JAR = "../impl/mvel/target/seam-reports-mvel.jar";
    public static final String SEAM_REPORTS_MVEL_POM = "../impl/mvel/pom.xml";
    
    public static final String SEAM_REPORTS_PENTAHO_JAR = "../impl/pentaho/target/seam-reports-pentaho.jar";
    public static final String SEAM_REPORTS_PENTAHO_POM = "../impl/pentaho/pom.xml";
    
    public static final String SEAM_REPORTS_XDOCREPORT_JAR = "../impl/xdocreport/target/seam-reports-xdocreport.jar";
    public static final String SEAM_REPORTS_XDOCREPORT_POM = "../impl/xdocreport/pom.xml";
     
    private static final MavenResolutionFilter seamReportsJarFilter = new MavenResolutionFilter() {
        @Override
        public boolean accept(MavenDependency element) {
            if (element.getCoordinates().startsWith("org.jboss.seam.reports:seam-reports-api")) {
                return false;
            }
            return true;
        }

        @Override
        public MavenResolutionFilter configure(Collection<MavenDependency> dependencies) {
            return this;
        }       
    };
    
    @Override
    public WebArchive jasperDeployment() {
        return ShrinkWrap.create(WebArchive.class)
        .addAsLibrary(
                ShrinkWrap.create(
                        ZipImporter.class, "seam-reports-jasper.jar")
                        .importFrom(new File(SEAM_REPORTS_JASPER_JAR))
                        .as(JavaArchive.class))
                        
        .addAsLibrary(
                ShrinkWrap.create(
                        ZipImporter.class, "seam-reports-api.jar")
                        .importFrom(new File(SEAM_REPORTS_API_JAR))
                        .as(JavaArchive.class))
                        
        .addAsLibraries(DependencyResolvers.use(MavenDependencyResolver.class)
                 .configureFrom(SETTINGS_XML)         
                 .loadMetadataFromPom("pom.xml")
                 .loadMetadataFromPom(SEAM_REPORTS_JASPER_POM)
                 .includeDependenciesFromPom(SEAM_REPORTS_JASPER_POM)
                 .artifact("net.sf.jpdfunit:jpdfunit")
                 .resolveAs(JavaArchive.class, seamReportsJarFilter))
        .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Override
    public WebArchive mvelDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addAsLibrary(
                    ShrinkWrap.create(ZipImporter.class, "seam-reports-mvel.jar")
                    .importFrom(new File(SEAM_REPORTS_MVEL_JAR))
                    .as(JavaArchive.class))
                    
            .addAsLibrary(
                ShrinkWrap.create(
                        ZipImporter.class, "seam-reports-api.jar")
                        .importFrom(new File(SEAM_REPORTS_API_JAR))
                        .as(JavaArchive.class))
                    
            .addAsLibraries(DependencyResolvers.use(MavenDependencyResolver.class)
                 .configureFrom(SETTINGS_XML)         
                 .loadMetadataFromPom("pom.xml")
                 .loadMetadataFromPom(SEAM_REPORTS_MVEL_POM)
                 .includeDependenciesFromPom(SEAM_REPORTS_MVEL_POM)     
                 .artifact("commons-logging:commons-logging")
                 .artifact("net.sf.jpdfunit:jpdfunit")
                 .resolveAs(JavaArchive.class, seamReportsJarFilter))
                 
        .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Override
    public WebArchive pentahoDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
            .addAsLibrary(
                    ShrinkWrap.create(ZipImporter.class, "seam-reports-pentaho.jar")
                    .importFrom(new File(SEAM_REPORTS_PENTAHO_JAR))
                    .as(JavaArchive.class))
                    
            .addAsLibrary(
                ShrinkWrap.create(
                        ZipImporter.class, "seam-reports-api.jar")
                        .importFrom(new File(SEAM_REPORTS_API_JAR))
                        .as(JavaArchive.class))
                    
            .addAsLibraries(DependencyResolvers.use(MavenDependencyResolver.class)
                 .configureFrom(SETTINGS_XML)         
                 .loadMetadataFromPom("pom.xml")
                 .loadMetadataFromPom(SEAM_REPORTS_PENTAHO_POM)
                 .includeDependenciesFromPom(SEAM_REPORTS_PENTAHO_POM)    
                 .artifact("commons-logging:commons-logging")
                 .artifact("net.sf.jpdfunit:jpdfunit")
                 .resolveAs(JavaArchive.class, seamReportsJarFilter));
    }

    @Override
    public WebArchive xdocreportDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addAsLibrary(
                    ShrinkWrap.create(
                            ZipImporter.class, "seam-reports-xdocreport.jar")
                            .importFrom(new File(SEAM_REPORTS_XDOCREPORT_JAR))
                            .as(JavaArchive.class))
            
            .addAsLibrary(
                ShrinkWrap.create(
                        ZipImporter.class, "seam-reports-api.jar")
                        .importFrom(new File(SEAM_REPORTS_API_JAR))
                        .as(JavaArchive.class))
            
            .addAsLibraries(DependencyResolvers.use(MavenDependencyResolver.class)
                 .configureFrom(SETTINGS_XML)         
                 .loadMetadataFromPom("pom.xml")
                 .loadMetadataFromPom(SEAM_REPORTS_XDOCREPORT_POM)
                 .includeDependenciesFromPom(SEAM_REPORTS_XDOCREPORT_POM)
                 .artifact("net.sf.jpdfunit:jpdfunit")
                 .artifact("org.apache.tika:tika-parsers")
                 .resolveAs(JavaArchive.class, seamReportsJarFilter))
            .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }
}
