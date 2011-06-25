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
package org.jboss.seam.reports.openoffice.framework.contenthandler;

import java.util.List;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.openoffice.framework.contenthandler.util.ComponentUtil;
import org.w3c.dom.Element;

public class OODefaultParagraphIterator<T> extends OODefaultComponentIterator<T> {

    private Element rootElement;

    public OODefaultParagraphIterator(String id, List<T> value) {
        super(id, value);
    }

    @Override
    public Element getRootElement() {
        if (null == rootElement) {
            List<Element> userFields = ComponentUtil.getUserFieldGetElements(getFacade().getContentElement(), getId());
            if (userFields.isEmpty()) {
                throw new ReportException(getId() + " component not found");
            }
            rootElement = (Element) ComponentUtil.anchestor(userFields.get(0), "text:p").getParentNode();
        }
        return rootElement;
    }

    @Override
    protected String getTemplateNodeName() {
        return "text:p";
    }

    @Override
    protected void performHide() {
        getRootElement().removeChild(getTemplateElement());
    }

}
