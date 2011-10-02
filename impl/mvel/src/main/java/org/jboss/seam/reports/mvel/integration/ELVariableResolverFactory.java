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

import javax.el.ValueExpression;
import javax.inject.Inject;

import org.jboss.solder.el.Expressions;
import org.mvel2.integration.VariableResolver;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.BaseVariableResolverFactory;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class ELVariableResolverFactory extends BaseVariableResolverFactory
{
   private static final long serialVersionUID = -6604385342199256006L;

   private final Expressions expressions;

   @Inject
   public ELVariableResolverFactory(final Expressions expressions)
   {
      this.nextFactory = null;
      this.expressions = expressions;
   }

   public ELVariableResolverFactory(final Expressions expressions, final VariableResolverFactory nextFactory)
   {
      this.nextFactory = nextFactory;
      this.expressions = expressions;
   }

   @Override
   public VariableResolver getVariableResolver(final String name)
   {
      if (isLocallyResolvable(name))
      {
         return new ELVariableResolver(expressions, name);
      }
      else if (getNextFactory() != null)
         return getNextFactory().getVariableResolver(name);
      else
         return super.getVariableResolver(name);
   }

   @Override
   public VariableResolver createVariable(final String name, final Object value)
   {
      if (isLocallyResolvable(name))
      {
         ValueExpression expression = expressions.getExpressionFactory().createValueExpression(value, Object.class);
         Class<?> type = expression.getType(expressions.getELContext());
         ELVariableResolver resolver = new ELVariableResolver(expressions, name, type);
         resolver.setValue(value);
         return resolver;
      }
      else if (isResolveable(name))
      {
         return getNextFactory().createVariable(name, value);
      }
      return null;
   }

   @Override
   public VariableResolver createVariable(final String name, final Object value, final Class<?> type)
   {
      if (isLocallyResolvable(name))
      {
         ELVariableResolver resolver = new ELVariableResolver(expressions, name, type);
         resolver.setValue(value);
         return resolver;
      }
      else if (isResolveable(name))
      {
         return getNextFactory().createVariable(name, value);
      }
      return null;
   }

   @Override
   public boolean isTarget(final String name)
   {
      return isLocallyResolvable(name);
   }

   @Override
   public boolean isResolveable(final String name)
   {
      boolean result = false;
      result = isLocallyResolvable(name) || isNextResolveable(name);
      return result;
   }

   private boolean isLocallyResolvable(final String name)
   {
      boolean result = false;
      if (name == null)
      {
         result = false;
      }
      try
      {
         expressions.evaluateValueExpression(expressions.toExpression(name));
         result = true;
      }
      catch (Exception e)
      {
      }

      return result;
   }

}
