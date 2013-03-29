package edu.swmed.qbrc.guiberest.shared.rest.util;

import java.lang.annotation.Annotation;
import org.jboss.resteasy.spi.StringParameterUnmarshaller;

public class StringArrayUnmarshaller implements StringParameterUnmarshaller<StringArray> {

	public StringArray fromString(String str) {
		return new StringArray(str);
	}

	public void setAnnotations(Annotation[] annotations) {
		// If you need to get a value from the annotation
		//StringArray stringArray = FindAnnotation.findAnnotation(annotations, StringArray.class);
		return;
	}
	
}