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
package org.jboss.seam.reports.jasperreports;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.ReportException;

public class JasperSeamReportDefinition implements ReportDefinition<JasperSeamReportDataSource, JasperSeamReport> {

    private JasperReport compiledReport;

    public JasperSeamReportDefinition(JasperReport compiledReport) {
        super();
        this.compiledReport = compiledReport;
    }

    public JasperReport getCompiledReport() {
        return compiledReport;
    }

    @Override
    public JasperSeamReport fill(JasperSeamReportDataSource dataSource, Map<String, Object> parameters)
            throws ReportException {
        try {
            JRDataSource ds = dataSource.getDelegate();
            JasperPrint filledReport = JasperFillManager.fillReport(getCompiledReport(), parameters,ds);
            return new JasperSeamReport(filledReport);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

}
