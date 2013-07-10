package edu.swmed.qbrc.guiberest.webapp.guice;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class DispatchServletModule extends ServletModule {
   	
	@Override
    public void configureServlets() {

		/* CAS Filters */
		bind(AuthenticationFilter.class).in(Singleton.class);
        bind(SingleSignOutFilter.class).in(Singleton.class);
        bind(Cas20ProxyReceivingTicketValidationFilter.class).in(Singleton.class);
        bind(HttpServletRequestWrapperFilter.class).in(Singleton.class);
        bind(AssertionThreadLocalFilter.class).in(Singleton.class);
		
        /* CAS Filters */
        filter("/*").through(SingleSignOutFilter.class);
        filter("/proxyCallback").through(Cas20ProxyReceivingTicketValidationFilter.class, getFilterProperties());
        filter("/*").through(AuthenticationFilter.class, getFilterProperties());
        filter("/*").through(Cas20ProxyReceivingTicketValidationFilter.class, getFilterProperties());
        filter("/*").through(HttpServletRequestWrapperFilter.class);
        filter("/*").through(AssertionThreadLocalFilter.class);
        /* END CAS Filters */
    }
	
    private Map<String, String> getFilterProperties() {
        InputStream inputStream = getClass().getResourceAsStream("/cas.properties");

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
