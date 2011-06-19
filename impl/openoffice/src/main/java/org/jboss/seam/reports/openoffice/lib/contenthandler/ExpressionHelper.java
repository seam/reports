package org.jboss.seam.reports.openoffice.lib.contenthandler;

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
