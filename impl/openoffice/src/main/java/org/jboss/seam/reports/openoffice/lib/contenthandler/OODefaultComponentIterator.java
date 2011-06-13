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
package org.jboss.seam.reports.openoffice.lib.contenthandler;

import static org.jboss.seam.reports.openoffice.lib.contenthandler.ComponentUtil.anchestor;
import static org.jboss.seam.reports.openoffice.lib.contenthandler.ComponentUtil.changeIds;
import static org.jboss.seam.reports.openoffice.lib.contenthandler.ComponentUtil.getChildElements;
import static org.jboss.seam.reports.openoffice.lib.contenthandler.ComponentUtil.getUserFieldGetElements;
import static org.jboss.seam.reports.openoffice.lib.contenthandler.ComponentUtil.resolveIterativeVar;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

abstract public class OODefaultComponentIterator extends OOContentHandlerBase {

    private String id;
    private String listVarName;
    private boolean hidden;

    public OODefaultComponentIterator(String id, String listVarName) {
        this.id = id;
        this.listVarName = listVarName;
    }

    abstract protected Element getRootElement();

    abstract protected String getTemplateNodeName();

    @Override
    public OODefaultComponentIterator hide() {
        hidden = true;
        return this;
    }

    protected String getId() {
        return id;
    }

    protected Element getTemplateElement() {
        Element itemVar = getUserFieldGetElements(getRootElement(), getId()).get(0);
        return anchestor(itemVar, getTemplateNodeName());
    }

    protected void appendElements(int size) {

        Element templateElement = getTemplateElement();

        for (int i = 0; i < size - 1; ++i) {
            Element cloned = (Element) templateElement.cloneNode(true);
            changeIds(cloned, i);
            getRootElement().insertBefore(cloned, templateElement);
        }

    }

    protected void resolveElements(List<?> list) {
        int index = 0;
        for (Element itemElement : getChildElements(getRootElement())) {

            List<Element> userVars = getUserFieldGetElements(itemElement, id);

            if (!userVars.isEmpty()) {
                Object item = list.get(index++);
                for (Element userVar : userVars) {
                    resolveIterativeVar(getFacade(), id, userVar, index, item);
                }
            }

        }
    }

    protected void performHide() {
        getRootElement().getParentNode().removeChild(getRootElement());
    }

    @Override
    public void render(Map<String, Object> vars) {

        if (hidden) {
            performHide();
            return;
        }

        List<?> list = (List<?>) vars.get(this.listVarName);

        appendElements(list.size());
        resolveElements(list);
    }

}
