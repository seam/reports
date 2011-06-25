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
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.jboss.seam.reports.ReportException;

public class ExpressionHelper {
    
    private String expression;
    private String firstPart;
    private List<String> propertyChain;
    
    public ExpressionHelper(String expression) {
        this.expression = expression;
    }
    
    public ExpressionHelper compile() {
        List<String> propertyChain = Arrays.asList(expression.split("\\."));
        if (propertyChain.isEmpty()) {
            return this;
        }
        this.firstPart = propertyChain.get(0);
        this.propertyChain = propertyChain.subList(1, propertyChain.size());
        return this;
    }
    
    public Object getValueFromContext(Map<String, Object> vars) {
        Object value = vars.get(firstPart);
        return getValue(value);
    }
    
    public Object getValue(Object value) {
        if (null == value) {
            return null;
        }
        Object currentValue = value;
        for (String propertyName : propertyChain) {
            try {
                currentValue = PropertyUtils.getProperty(currentValue, propertyName);
            } catch (Exception e) {
                throw new ReportException(e);
            }    
        }
        return currentValue;
    }
    
    public String getFirstPart() {
        return firstPart;
    }

}
