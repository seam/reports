package org.jboss.seam.reports.jasperreports.qualifiers.test;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import org.jboss.seam.reports.annotations.frameworks.JasperReports;
import org.jboss.seam.solder.resourceLoader.Resource;

@Qualifier
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@Documented
@Resource("XlsDataSourceReport.jrxml")
@JasperReports
public @interface SalesReport {

}
