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
package org.jboss.seam.reports.openoffice.framework.contenthandler.context;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.reports.openoffice.framework.OdfToolkitFacade;
import org.jboss.seam.reports.openoffice.framework.contenthandler.OOContentHandler;
import org.jboss.seam.reports.openoffice.framework.contenthandler.util.IdentifierHelper;
import org.jboss.seam.reports.openoffice.framework.contenthandler.util.IdentifierHelper.AttributeMatch;
import org.odftoolkit.simple.table.Table;
import org.w3c.dom.Element;

public class IterationContextImpl implements IterationContext {

    private int index;
    private OdfToolkitFacade facade;
    private List<OOContentHandler> handlers = new ArrayList<OOContentHandler>();

    public IterationContextImpl(int index, OdfToolkitFacade facade) {
        super();
        this.index = index;
        this.facade = facade;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public OdfToolkitFacade getFacade() {
        return facade;
    }

    protected IdentifierHelper getIdHelper() {
        return new IdentifierHelper(getIndex());
    }

    @Override
    public Element getElementById(String id) {
        return facade.getContentElement().getOwnerDocument().getElementById(getIdHelper().transformId(id));
    }

    @Override
    public Table getTable(String name) {
        return getFacade().getDocument().getTableByName(getIdHelper().transformId(name));
    }

    @Override
    public void add(OOContentHandler contentHandler) {
        contentHandler.setFacade(getFacade());
        IdentifierHelper helper = new IdentifierHelper(getIndex(), new AttributeMatch("text:user-field-get", "text:name"));
        helper.updateIdentifiers(contentHandler.getRootElement());
        
        handlers.add(contentHandler);
    }

    public List<OOContentHandler> getHandlers() {
        return handlers;
    }

}
