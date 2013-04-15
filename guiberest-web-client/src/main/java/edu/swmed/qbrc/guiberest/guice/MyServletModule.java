package edu.swmed.qbrc.guiberest.guice;

import java.util.Properties;
import org.reflections.Reflections;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.GuiberestSerializationModule;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonConfigProvider;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonSerializationModule;
import edu.swmed.qbrc.jacksonate.rest.jackson.ReflectionFactory;
import edu.swmed.qbrc.jacksonate.rest.jackson.RestBaseUrl;

public class MyServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		Names.bindProperties(binder(), loadProperties());
		
		bind(ReflectionFactory.class).in(Scopes.SINGLETON);
		bind(Reflections.class).toInstance(new Reflections("edu.swmed.qbrc.guiberest.shared.domain.guiberest"));

		// Bind the RestBaseUrl class to an instance with the property for the base URL
		bind(RestBaseUrl.class).toInstance(new RestBaseUrl("", ""));

		// We can simply bind the JacksonConfigProvider here, which sets up the Jackson custom serializers.
		bind(JacksonSerializationModule.class).to(GuiberestSerializationModule.class).in(Scopes.SINGLETON);
		bind(JacksonConfigProvider.class);

		bind(GuiberestRestService.class).toProvider(GuiberestRestServiceProvider.class).in(Scopes.SINGLETON);
		
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