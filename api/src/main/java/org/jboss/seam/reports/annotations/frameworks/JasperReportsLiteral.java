package org.jboss.seam.reports.annotations.frameworks;

import javax.enterprise.util.AnnotationLiteral;

public class JasperReportsLiteral extends AnnotationLiteral<JasperReports> implements JasperReports {

    public static final JasperReports INSTANCE = new JasperReportsLiteral();

}
