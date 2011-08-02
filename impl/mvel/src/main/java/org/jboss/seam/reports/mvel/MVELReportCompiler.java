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

import java.io.InputStream;

import javax.inject.Inject;

import org.jboss.seam.reports.ReportCompiler;
import org.jboss.seam.reports.exceptions.ReportException;
import org.jboss.seam.reports.mvel.annotations.MVEL;
import org.jboss.seam.reports.mvel.integration.ELVariableResolverFactory;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;

@MVEL
public class MVELReportCompiler implements ReportCompiler
{
   @Inject
   private ELVariableResolverFactory variableResolverFactory;

   @Override
   public MVELReportDefinition compile(InputStream input) throws ReportException
   {
      CompiledTemplate compiledTemplate = TemplateCompiler.compileTemplate(input);
      return new MVELReportDefinition(compiledTemplate, variableResolverFactory);
   }

   @Override
   public MVELReportDefinition compile(String name) throws ReportException
   {
      CompiledTemplate compiledTemplate = TemplateCompiler.compileTemplate(name);
      return new MVELReportDefinition(compiledTemplate, variableResolverFactory);
   }

}
