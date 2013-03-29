package edu.swmed.qbrc.guiberest.guice;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface GuiberestDataSource{
	String info() default "";
}

