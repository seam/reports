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

import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;

public class IdentifierHelper {

    public static class AttributeMatch {

        String nodeName;
        String attributeName;

        public AttributeMatch(String nodeName, String attributeName) {
            this.nodeName = nodeName;
            this.attributeName = attributeName;
        }

        public AttributeMatch(String attributeName) {
            this.attributeName = attributeName;
        }

        private String nullifyEmptyString(String s) {
            return "".equals(s) ? null : s;
        }

        String getMatchingValue(Element element) {
            if (nodeName == null) {
                return nullifyEmptyString(element.getAttribute(attributeName));
            } else if (element.getNodeName().equals(nodeName)) {
                return nullifyEmptyString(element.getAttribute(attributeName));
            }
            return null;
        }

    }

    private int index;

    private List<AttributeMatch> attributeNames;

    public IdentifierHelper(int index, AttributeMatch... attributeNames) {
        this.index = index;
        if (attributeNames.length == 0) {
            setAttributeNames(new AttributeMatch("xml:id"), new AttributeMatch("table:name"));
        } else {
            this.setAttributeNames(attributeNames);
        }
    }

    public void setAttributeNames(AttributeMatch... attributeNames) {
        this.attributeNames = Arrays.asList(attributeNames);
    }

    public String transformId(String id) {
        return id + "_" + index;
    }

    public String originalId(String id) {
        return id.substring(0, id.lastIndexOf('_'));
    }

    public void updateIdentifiers(Element element) {
        for (AttributeMatch match : attributeNames) {
            String id = match.getMatchingValue(element);
            if (id != null) {
                element.setAttribute(match.attributeName, transformId(id));
            }
        }

        for (Element child : ComponentUtil.getChildElements(element)) {
            updateIdentifiers(child);
        }

    }
 

}
