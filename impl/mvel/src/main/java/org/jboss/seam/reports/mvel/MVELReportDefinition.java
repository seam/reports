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
package org.jboss.seam.reports.mvel;

import java.util.Map;

import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.exceptions.ReportException;
import org.jboss.seam.reports.spi.CharSequenceReport;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateRuntime;

/**
 * A {@link ReportDefinition} for a {@link CompiledTemplate} object
 * 
 * @author george
 * 
 */
public class MVELReportDefinition implements ReportDefinition
{

   private CompiledTemplate compiledTemplate;
   private VariableResolverFactory variableResolverFactory;

   public MVELReportDefinition(CompiledTemplate compiledTemplate, VariableResolverFactory variableResolverFactory)
   {
      super();
      this.compiledTemplate = compiledTemplate;
      this.variableResolverFactory = variableResolverFactory;
   }

   @Override
   public Report fill(Object dataSource, Map<String, Object> parameters) throws ReportException
   {

      Object data = TemplateRuntime.execute(compiledTemplate.getRoot(), compiledTemplate.getTemplate(),
               new StringBuilder(), dataSource, variableResolverFactory, null);
      return new CharSequenceReport(data.toString(), this);
   }

   public CompiledTemplate getDelegate()
   {
      return compiledTemplate;
   }

   public VariableResolverFactory getVariableResolverFactory()
   {
      return variableResolverFactory;
   }
}
