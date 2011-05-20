package org.jboss.seam.reports.spi.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Meta annotation to define supported report frameworks
 * 
 * @author George Gastaldi
 * 
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface ReportBinding {

}
