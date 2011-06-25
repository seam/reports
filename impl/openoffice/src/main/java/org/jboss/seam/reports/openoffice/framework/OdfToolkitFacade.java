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
package org.jboss.seam.reports.openoffice.framework;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.openoffice.framework.contenthandler.OOContentHandler;
import org.jboss.seam.reports.openoffice.framework.contenthandler.util.ComponentUtil;
import org.jboss.seam.reports.openoffice.framework.contenthandler.util.ExpressionHelper;
import org.odftoolkit.odfdom.dom.OdfStylesDom;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.field.Fields;
import org.odftoolkit.simple.common.field.VariableContainer;
import org.odftoolkit.simple.common.field.VariableField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OdfToolkitFacade {

    private TextDocument document;

    // XXX: current OdfToolkit API lacks a way to access this object
    private VariableContainer varContainer;

    // XXX: hack to get variable container
    private void initContainer() {
        Method method;
        try {
            method = document.getClass().getDeclaredMethod("getVariableContainerImpl");
        } catch (Exception e) {
            throw new ReportException(e);
        }
        method.setAccessible(true);

        try {
            varContainer = (VariableContainer) method.invoke(document);
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

    public OdfToolkitFacade(InputStream is) {
        try {
            document = TextDocument.loadDocument(is);
        } catch (Exception e) {
            throw new ReportException(e);
        }
        initContainer();
    }

    public OdfToolkitFacade(String path) {
        try {
            document = TextDocument.loadDocument(path);
        } catch (Exception e) {
            throw new ReportException(e);
        }
        initContainer();
    }

    public VariableField fillVar(String name, Object value) {
        VariableField var = document.getVariableFieldByName(name);
        if (null == var) {
            return null; 
        }
        var.updateField((String) value, null);
        return var;
    }

    protected void fillExpressions(Map<String, Object> vars) {
        for (Element el : ComponentUtil.getElementsByTagName(this.varContainer.getVariableContainerElement(), "text:user-field-decl")) {
            String name = el.getAttribute("text:name");
            ExpressionHelper helper = new ExpressionHelper(name);
            Object value = helper.compile().getValueFromContext(vars);
            fillVar(name, value);
        }
        
    }



    public void fillData(Map<String, Object> vars) {
        if (null == vars) {
            return;
        }
        fillExpressions(vars);
    }

    public void fillData(List<OOContentHandler> components, Map<String, Object> vars) {
        fillData(vars);

        if (null == components) {
            return;
        }

        for (OOContentHandler component : components) {
            component.setFacade(this);
            component.render(vars);
        }

    }

    public void addBackgroundImage(URI image, String packagePath, String mediaType,
            BackgroundImageOptions backgroundImageOptions) {
        try {
            document.getPackage().insert(image, packagePath, mediaType);
            OdfStylesDom styles = document.getStylesDom();
            Element pageLayoutPropertiesElement = (Element) styles.getAutomaticStyles()
                    .getPageLayout(backgroundImageOptions.getPageLayoutName())
                    .getElementsByTagName("style:page-layout-properties").item(0);
            Document documentNode = styles.getRootElement().getOwnerDocument();
            Element backgroundImageElement = documentNode.createElementNS("urn:oasis:names:tc:opendocument:xmlns:style:1.0",
                    "style:background-image");
            backgroundImageElement.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", packagePath);
            backgroundImageElement.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:type", "simple");
            backgroundImageElement.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:actuate", "onLoad");
            pageLayoutPropertiesElement.appendChild(backgroundImageElement);
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

    public void addBackgroundImage(URI image, String packagePath, String mediaType) {
        addBackgroundImage(image, packagePath, mediaType, new BackgroundImageOptions());
    }

    public TextDocument getDocument() {
        return document;
    }

    public Element getContentElement() {
        try {
            return document.getContentDom().getDocumentElement();
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

    public VariableField createSimpleVar(String name, Object value) {
        VariableField var = Fields.createUserVariableField(varContainer, name, name);
        var.updateField(value == null ? "" : value.toString(), null);
        return var;
    }

}
