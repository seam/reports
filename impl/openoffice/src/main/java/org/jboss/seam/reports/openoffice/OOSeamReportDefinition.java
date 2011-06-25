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

import java.util.Map;

import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.openoffice.lib.OdfToolkitFacade;

public class OOSeamReportDefinition implements ReportDefinition<OOSeamReportDataSource, OOSeamReport> {

    private OdfToolkitFacade odfFacade;

    public OOSeamReportDefinition(OdfToolkitFacade odfFacade) {
        this.odfFacade = odfFacade;
    }

    @Override
    public OOSeamReport fill(OOSeamReportDataSource dataSource, Map<String, Object> parameters) throws ReportException {
        if (dataSource == null) {
            odfFacade.fillData(parameters);
        } else {
            odfFacade.fillData(dataSource.getDelegate(), parameters);
        }
        return new OOSeamReport(odfFacade);
    }

}
