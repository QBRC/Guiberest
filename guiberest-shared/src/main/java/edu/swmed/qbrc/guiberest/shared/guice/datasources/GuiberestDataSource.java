package edu.swmed.qbrc.guiberest.shared.guice.datasources;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface GuiberestDataSource{
	String info() default "";
}

