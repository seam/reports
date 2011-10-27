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

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import org.jboss.seam.reports.exceptions.ReportException;
import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;

/**
 * Pentaho Reporting bootstrap to initialize the Reporting Engine at application startup
 * 
 * @author Jordan Ganoff
 */
public class PentahoReportingExtension implements Extension
{

   public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd)
   {
      try
      {
         ClassicEngineBoot.getInstance().start();
      }
      catch (Throwable t)
      {
         abd.addDefinitionError(new ReportException("Error loading Pentaho Reporting Engine",t));
      }
   }
}
