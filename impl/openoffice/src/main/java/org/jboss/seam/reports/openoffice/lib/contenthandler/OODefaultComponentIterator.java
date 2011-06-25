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
import static org.jboss.seam.reports.openoffice.lib.contenthandler.ComponentUtil.getChildElements;
import static org.jboss.seam.reports.openoffice.lib.contenthandler.ComponentUtil.getUserFieldGetElements;
import static org.jboss.seam.reports.openoffice.lib.contenthandler.ComponentUtil.resolveIterativeVar;

import java.util.List;
import java.util.Map;

import org.jboss.seam.reports.ReportException;
import org.w3c.dom.Element;

abstract public class OODefaultComponentIterator<T> extends OOContentHandlerBase {

    private List<T> value;
    private boolean hidden;
    private IterationHandler<T> iterationHandler = new DefaultIterationHandler<T>();
    
    public OODefaultComponentIterator(String id, List<T> value) {
        super(id);
        this.value = value;
    }

    abstract protected String getTemplateNodeName();

    @Override
    public OODefaultComponentIterator<T> hide() {
        hidden = true;
        return this;
    }

    protected Element getTemplateElement() {
        List<Element> userFieldElements = getUserFieldGetElements(getRootElement(), getId());
        if (userFieldElements.isEmpty()) {
            throw new ReportException("user elements not found");
        }
        Element itemVar = userFieldElements.get(0);
        return anchestor(itemVar, getTemplateNodeName());
    }

    protected void appendElements(int size) {

        Element templateElement = getTemplateElement();

        for (int i = 0; i < size - 1; ++i) {
            Element cloned = (Element) templateElement.cloneNode(true);
            new IdentifierHelper(i).updateIdentifiers(cloned);
            getRootElement().insertBefore(cloned, templateElement);
        }
        new IdentifierHelper(size - 1).updateIdentifiers(templateElement);

    }

    protected void resolveElements(List<T> list) {
        int index = 0;
        for (Element itemElement : getChildElements(getRootElement())) {

            List<Element> userVars = getUserFieldGetElements(itemElement, getId());

            if (!userVars.isEmpty()) {
                T item = list.get(index);
                for (Element userVar : userVars) {
                    resolveIterativeVar(getFacade(), getId(), userVar, index, item);
                }
                IterationContextImpl itContext = new IterationContextImpl(index, getFacade());
                iterationHandler.afterIteration(itContext, item);
                                
                getFacade().fillData(itContext.getHandlers(), null);
                index++;
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

        appendElements(this.value.size());
        resolveElements(this.value);
    }

    public void setIterationHandler(IterationHandler<T> iterationHandler) {
        this.iterationHandler = iterationHandler;
    }

    public IterationHandler<T> getIterationHandler() {
        return iterationHandler;
    }

}
