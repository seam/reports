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

import java.io.InputStream;

import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.openoffice.framework.OdfToolkitFacade;

@OOReports
public class OOSeamReportLoader implements ReportLoader {

    @Override
    public ReportDefinition<OOSeamReportDataSource, OOSeamReport> loadReportDefinition(InputStream input)
            throws ReportException {
        OdfToolkitFacade odfFacade = new OdfToolkitFacade(input);
        OOSeamReportDefinition report = new OOSeamReportDefinition(odfFacade);
        return report;
    }

    @Override
    public ReportDefinition<OOSeamReportDataSource, OOSeamReport> loadReportDefinition(String name) throws ReportException {
        OdfToolkitFacade odfFacade = new OdfToolkitFacade(name);
        OOSeamReportDefinition report = new OOSeamReportDefinition(odfFacade);
        return report;
    }

    @Override
    public Report loadReport(InputStream input) throws ReportException {
        OdfToolkitFacade odfFacade = new OdfToolkitFacade(input);
        OOSeamReport report = new OOSeamReport(odfFacade);
        return report;
    }

    @Override
    public Report loadReport(String name) throws ReportException {
        OdfToolkitFacade odfFacade = new OdfToolkitFacade(name);
        OOSeamReport report = new OOSeamReport(odfFacade);
        return report;
    }

}
