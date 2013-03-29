package edu.swmed.qbrc.guiberest.guice;

import com.google.inject.Binder;
import com.google.inject.Module;

import edu.swmed.qbrc.guiberest.rest.impl.GuiberestRestServiceImpl;

public class RestModule implements Module {

	public void configure(final Binder binder) {
		binder.install(new GuiberestDataSourcePersistModule());
		binder.bind(GuiberestRestServiceImpl.class);
	}
	
}