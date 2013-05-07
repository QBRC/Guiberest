package edu.swmed.qbrc.guiberest.guice;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import edu.swmed.qbrc.auth.cashmac.server.filters.CasHmacRequestFilter;
import edu.swmed.qbrc.guiberest.guice.datasources.GuiberestDataSourcePersistModule;

public class DispatchServletModule extends ServletModule {
   	
	@Override
    public void configureServlets() {

		/* Serve all requests with RESTEasy's HttpServletDispatcher */
		bind(HttpServletDispatcher.class).in(Singleton.class);
		serve("/*").with(HttpServletDispatcher.class);
		
		/* Bind CasHmacRequestFilter so CasHmac's Hibernate interceptor can access the session for user information. */
		bind(CasHmacRequestFilter.class).in(Singleton.class);

		/* Filter all requests through our custom PersistModule (sets up Hibernate
		 * environment automatically). */
        filter("/*").through(GuiberestDataSourcePersistModule.GUIBEREST_DATA_SOURCE_FILTER_KEY);
        filter("/*").through(CasHmacRequestFilter.class);
    }
}
