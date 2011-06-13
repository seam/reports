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
package org.jboss.seam.reports.openoffice.util;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import org.jboss.seam.reports.ReportException;
import org.odftoolkit.odfdom.dom.OdfStylesDom;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.field.VariableField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OdfToolkitFacade {

    private TextDocument document;

    public OdfToolkitFacade(InputStream is) {
        try {
            document = TextDocument.loadDocument(is);
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

    public OdfToolkitFacade(String path) {
        try {
            document = TextDocument.loadDocument(path);
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

    public VariableField fillVar(String name, Object value) {
        VariableField var = document.getVariableFieldByName(name);
        var.updateField((String) value, null);
        return var;
    }

    public void fillData(Map<String, Object> vars) {
        for (Map.Entry<String, Object> e : vars.entrySet()) {
            fillVar(e.getKey(), e.getValue());
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

}
