package org.jboss.seam.reports.spi;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.jboss.seam.reports.ReportRenderer;

/**
 * Used on {@link ReportRenderer}
 * 
 * @author george
 * 
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface ReportOutputBinding {

    /**
     * The value provides a hint to the underlying renderer
     */
    String value();

    String mimeType() default "*";

}
