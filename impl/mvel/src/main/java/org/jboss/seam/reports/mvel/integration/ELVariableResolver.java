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
package org.jboss.seam.reports.mvel.integration;

import javax.el.ELContext;
import javax.el.ValueExpression;

import org.jboss.solder.el.Expressions;
import org.mvel2.integration.VariableResolver;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class ELVariableResolver implements VariableResolver
{
   private static final long serialVersionUID = 7393032041693001458L;

   private final Expressions expressions;
   private final String name;
   private final Class<?> type;
   private final ELContext context;

   public ELVariableResolver(final Expressions expressions, final String name, final Class<?> type)
   {
      this.expressions = expressions;
      context = expressions.getELContext();
      this.name = name;
      this.type = type;
   }

   public ELVariableResolver(final Expressions expressions, final String name)
   {
      this.expressions = expressions;
      this.context = expressions.getELContext();
      this.name = name;
      this.type = Object.class;
   }

   @Override
   public String getName()
   {
      return name;
   }

   @Override
   public Class<?> getType()
   {
      return type;
   }

   @Override
   @SuppressWarnings("rawtypes")
   public void setStaticType(final Class type)
   {

   }

   @Override
   public int getFlags()
   {
      return 0;
   }

   @Override
   public Object getValue()
   {
      ValueExpression expression = expressions.getExpressionFactory().createValueExpression(context,
               expressions.toExpression(name), type);
      Object value = expression.getValue(context);
      return value;
   }

   @Override
   public void setValue(final Object value)
   {
      ValueExpression expression = expressions.getExpressionFactory().createValueExpression(context,
               expressions.toExpression(name), type);
      expression.setValue(context, value);
   }

}
