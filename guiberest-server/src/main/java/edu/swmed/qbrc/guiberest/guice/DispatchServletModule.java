package edu.swmed.qbrc.guiberest.guice;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

import edu.swmed.qbrc.guiberest.guice.datasources.GuiberestDataSourcePersistModule;

public class DispatchServletModule extends ServletModule {
   	
	@Override
    public void configureServlets() {

		/* Serve all requests with RESTEasy's HttpServletDispatcher */
		bind(HttpServletDispatcher.class).in(Singleton.class);
		serve("/*").with(HttpServletDispatcher.class);

		/* Filter all requests through our custom PersistModule (sets up Hibernate
		 * environment automatically). */
        filter("/*").through(GuiberestDataSourcePersistModule.GUIBEREST_DATA_SOURCE_FILTER_KEY);
    }
}
