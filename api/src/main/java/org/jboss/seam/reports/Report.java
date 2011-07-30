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
package org.jboss.seam.reports;

import java.io.Serializable;

/**
 * A "ready-to-print" report. Represents and instance of a {@link ReportDefinition}. This object may be rendered on a
 * {@link ReportRenderer}
 * 
 * @author George Gastaldi
 * 
 */
public interface Report extends Serializable
{
   /**
    * Return the definition (if available) that this report was based
    * 
    * @return the {@link ReportDefinition} used to create this {@link Report} or null if not available
    */
   public ReportDefinition getReportDefinition();

   /**
    * Return the underlying provider object for the {@link Report}, if available
    * 
    * @return The result of this method is implementation specific
    */
   public Object getDelegate();
}
