package edu.swmed.qbrc.guiberest.webapp.guice;

import java.util.Properties;
import org.reflections.Reflections;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
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

		// Support multiple users
		bind(ClientIdentification.class).toProvider(ClientIdentificationProvider.class).in(Singleton.class);
		
		// Bind the RestBaseUrl class to an instance with the property for the base URL
		bind(RestBaseUrl.class).toInstance(new RestBaseUrl("", ""));

		// We can simply bind the JacksonConfigProvider here, which sets up the Jackson custom serializers.
		bind(JacksonSerializationModule.class).to(GuiberestSerializationModule.class).in(Scopes.SINGLETON);
		bind(JacksonConfigProvider.class);

		bind(GuiberestRestService.class).toProvider(GuiberestRestServiceProvider.class).in(Scopes.SINGLETON);
		
		bind(TestableEntityTester.class).toProvider(TestableEntityTesterProvider.class).in(Singleton.class);

		serve("/logout/*").with(LogoutServlet.class);
		serve("/logout").with(LogoutServlet.class);
		serve("/info/*").with(Info.class);
		serve("/*").with(MyServlet.class);
		//super.configureServlets();
	}

	/**
	 * Modify this function to return the appropriate properties for your application.
	 * @return
	 */
	private Properties loadProperties() {
		String hostname = "localhost";
		try {
			hostname = java.net.InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {}
    	Properties props = new Properties();
    	
		props.setProperty("ClientId-thomas", "thomas-test");
		props.setProperty("Secret-thomas", "");
		props.setProperty("ClientId-roger", "roger-test");
		props.setProperty("Secret-roger", "");
		props.setProperty("ClientId-sean", "sean-test");
		props.setProperty("Secret-sean", "");
		props.setProperty("ClientId-irsauditer", "irsauditer-test");
		props.setProperty("Secret-irsauditer", "");
		props.setProperty("ClientId-guest", "guest-test");
		props.setProperty("Secret-guest", "");
		props.setProperty("ClientId-cook", "cook-test");
		props.setProperty("Secret-cook", "");
		
		props.setProperty("usingCAS", "true");
    	props.setProperty("HostName", hostname + ":9090"); // The Host name of the RESTful service (not the client; don't include http://)
    	props.setProperty("RestURL", "https://" + hostname + ":9090");
    	props.setProperty("usingCAS", "true");//Flag for using CAS
    	return props;
	}
	
}