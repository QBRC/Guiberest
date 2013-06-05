package edu.swmed.qbrc.guiberest.guice;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Saml11TicketValidationFilter;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import edu.swmed.qbrc.auth.cashmac.server.acl.utils.CasHmacValidation;
import edu.swmed.qbrc.auth.cashmac.server.filters.CasHmacRequestFilter;
import edu.swmed.qbrc.auth.cashmac.server.guice.CasHmacValidationProvider;
import edu.swmed.qbrc.guiberest.guice.datasources.GuiberestDataSourcePersistModule;

public class DispatchServletModule extends ServletModule {
   	
	@Override
    public void configureServlets() {

		/* Serve all requests with RESTEasy's HttpServletDispatcher */
		bind(HttpServletDispatcher.class).in(Singleton.class);
		serve("/*").with(HttpServletDispatcher.class);
		
		/* Bind CasHmacRequestFilter so CasHmac's Hibernate interceptor can access the session for user information.
		 * This also makes Guiberest's CasHmac properties available to CasHmac. */
		bind(CasHmacRequestFilter.class).in(Singleton.class);
        filter("/*").through(CasHmacRequestFilter.class, getFilterProperties());

		/* CAS Filters */
        bind(SingleSignOutFilter.class).in(Singleton.class);
        //bind(Cas20ProxyReceivingTicketValidationFilter.class).in(Singleton.class);
        bind(Saml11TicketValidationFilter.class).in(Singleton.class);
        bind(HttpServletRequestWrapperFilter.class).in(Singleton.class);
        bind(AssertionThreadLocalFilter.class).in(Singleton.class);
		
		/* Bind CasHmacValidation so we can access CasHmac utility methods. */
		bind(CasHmacValidation.class).toProvider(CasHmacValidationProvider.class);
		
        /* CAS Filters */
        filter("/*").through(SingleSignOutFilter.class);
        //filter("/*").through(Cas20ProxyReceivingTicketValidationFilter.class, getFilterProperties());
        filter("/*").through(Saml11TicketValidationFilter.class, getFilterProperties());
        filter("/*").through(HttpServletRequestWrapperFilter.class);
        filter("/*").through(AssertionThreadLocalFilter.class);
        /* END CAS Filters */
        
		/* Filter all requests through our custom PersistModule (sets up Hibernate
		 * environment automatically). */
        filter("/*").through(GuiberestDataSourcePersistModule.GUIBEREST_DATA_SOURCE_FILTER_KEY);
    }
	
    private Map<String, String> getFilterProperties() {
        InputStream inputStream = getClass().getResourceAsStream("/cashmac.properties");

        if (inputStream == null) {
            throw new RuntimeException();
        }

        try {
    		Map<String, String> filterProps = new HashMap<String, String>();
            Properties properties = new Properties();
            properties.load(inputStream);

            for (Object key : properties.keySet()) {
            	filterProps.put((String)key, (String)properties.get(key));
            }
            return filterProps;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }	
	
}
