/**
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual
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
package org.jboss.seam.reports;

import java.io.InputStream;

/**
 * Loads a report from a {@link InputStream}
 * 
 * The report may required to be compiled first. In these cases, the compile method should be invoked then.
 * 
 * @author george
 * 
 */
public interface ReportLoader {

    // TODO: Should these compile methods be moved as it doesn´t make sense to some reporting implementations ?
    Report<? extends ReportDataSource, ? extends ReportInstance> compile(InputStream input) throws ReportException;

    Report<? extends ReportDataSource, ? extends ReportInstance> compile(String name) throws ReportException;

    Report<? extends ReportDataSource, ? extends ReportInstance> loadReport(InputStream input) throws ReportException;

    Report<? extends ReportDataSource, ? extends ReportInstance> loadReport(String name) throws ReportException;

    ReportInstance loadReportInstance(InputStream input) throws ReportException;

    ReportInstance loadReportInstance(String name) throws ReportException;
}
