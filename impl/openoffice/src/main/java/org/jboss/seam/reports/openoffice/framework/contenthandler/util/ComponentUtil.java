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
package org.jboss.seam.reports.openoffice.framework.contenthandler.util;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.openoffice.framework.OdfToolkitFacade;
import org.odftoolkit.simple.common.field.VariableField;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComponentUtil {

    static public boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    static public List<Element> getChildElements(Element element) {
        List<Element> result = new ArrayList<Element>();
        NodeList list = element.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            Node n = list.item(i);
            if (n instanceof Element) {
                result.add((Element) n);
            }
        }
        return result;
    }
    
    static public List<Element> getElementsByTagName(Element element, String childName) {
        List<Element> result = new ArrayList<Element>();
        NodeList list = element.getElementsByTagName(childName);
        for (int i = 0; i < list.getLength(); ++i) {
            Node n = list.item(i);
            if (n instanceof Element && n.getNodeName().equals(childName)) {
                result.add((Element) n);
            }
        }
        return result;
    }
    

    static public void notNull(Object component, String componentId) {
        if (null == component) {
            throw new ReportException(componentId + ": component not found");
        }
    }

    static public List<Element> getUserFieldGetElements(Element node, String componentId) {
        List<Element> result = new ArrayList<Element>();
        NodeList nodeList = node.getElementsByTagName("text:user-field-get");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Element currentElement = (Element) nodeList.item(i);
            String name = currentElement.getAttribute("text:name");
            if (null == componentId || name.equals(componentId) || name.startsWith(componentId + ".")) {
                result.add(currentElement);
            }
        }
        return result;
    }

    static public Element anchestor(Element element, String nodeName) {
        if (element.getNodeName().equals(nodeName)) {
            return element;
        }
        Element current = element;
        while (!current.getNodeName().equals(nodeName)) {
            current = (Element) current.getParentNode();
        }
        return current;
    }

    static public int getChildIndex(Element container, Element child) {
        NodeList children = container.getChildNodes();
        int index = 0;
        for (int i = 0; i < children.getLength(); ++i) {
            Node element = children.item(i);
            if (child.getNodeName().equals(element.getNodeName())) {
                if (child == element) {
                    return index;
                }
                index++;
            }
        }
        throw new IllegalStateException("child not found");
    }

    static public VariableField resolveIterativeVar(OdfToolkitFacade documentHandler, String prefix, Element userVar,
            int index, Object item) {
        
        String name = userVar.getAttribute("text:name");
        ExpressionHelper helper = new ExpressionHelper(name);
        Object value = helper.compile().getValue(item);
        VariableField varField = documentHandler.createSimpleVar(userVar.getAttribute("text:name") + "->" + index, value);
        userVar.setAttribute("text:name", varField.getVariableName());
        return varField;
    }
    
   
 

}
