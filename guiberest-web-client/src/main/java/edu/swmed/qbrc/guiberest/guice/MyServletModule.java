package edu.swmed.qbrc.guiberest.guice;

import java.util.Properties;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.ReflectionFactory;

public class MyServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		Names.bindProperties(binder(), loadProperties());
		bind(GuiberestRestService.class).toProvider(GuiberestRestServiceProvider.class).in(Scopes.SINGLETON);
		bind(ReflectionFactory.class).in(Scopes.SINGLETON);
		serve("/*").with(MyServlet.class);
		super.configureServlets();
	}

	/**
	 * Modify this function to return the appropriate properties for your application.
	 * @return
	 */
	private Properties loadProperties() {
    	Properties props = new Properties();
    	props.setProperty("ClientId", "thomas"); // Your Client Id (public)
    	props.setProperty("Secret", "123456789");// Your Client Secret (private)
    	props.setProperty("HostName", "127.0.0.1:9090"); // The Host name of the RESTful service (not the client; don't include http://)
    	props.setProperty("RestURL", "http://127.0.0.1:9090");
    	return props;
	}
	
}