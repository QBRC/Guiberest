package edu.swmed.qbrc.guiberest.shared.rest.util;

import java.lang.annotation.Annotation;
import org.jboss.resteasy.spi.StringParameterUnmarshaller;

public class IntegerArrayUnmarshaller implements StringParameterUnmarshaller<IntegerArray> {

	public IntegerArray fromString(String str) {
		return new IntegerArray(str);
	}

	public void setAnnotations(Annotation[] annotations) {
		// If you need to get a value from the annotation
		//IntegerArray integerArray = FindAnnotation.findAnnotation(annotations, IntegerArray.class);
		return;
	}
	
}