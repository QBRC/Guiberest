package edu.swmed.qbrc.guiberest.guice;

import com.google.inject.Inject;
import com.google.inject.PrivateModule;
import com.google.inject.persist.PersistService;

public class PersistenceInitModule extends PrivateModule {

	@Override
	protected void configure() {
	    bind(PersistenceInit.class).asEagerSingleton();
	}
	
	private static class PersistenceInit {
		@Inject
		PersistenceInit(PersistService service) {
			service.start();
		}
	}
}