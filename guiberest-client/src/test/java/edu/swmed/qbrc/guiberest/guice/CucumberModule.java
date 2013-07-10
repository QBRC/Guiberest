package edu.swmed.qbrc.guiberest.guice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.reflections.Reflections;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.GuiberestSerializationModule;
import edu.swmed.qbrc.guiberest.webapp.guice.ClientIdentification;
import edu.swmed.qbrc.guiberest.webapp.guice.ClientIdentificationProvider;
import edu.swmed.qbrc.guiberest.webapp.guice.TestableEntityTester;
import edu.swmed.qbrc.guiberest.webapp.guice.TestableEntityTesterProvider;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonConfigProvider;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonSerializationModule;
import edu.swmed.qbrc.jacksonate.rest.jackson.ReflectionFactory;
import edu.swmed.qbrc.jacksonate.rest.jackson.RestBaseUrl;

public class CucumberModule extends AbstractModule {

	@Override
    protected void configure() {
		Names.bindProperties(binder(), loadProperties());
		
		bind(ReflectionFactory.class).in(Singleton.class);
		bind(Reflections.class).toInstance(new Reflections("edu.swmed.qbrc.guiberest.shared.domain.guiberest"));

		// Support multiple users
		bind(ClientIdentification.class).toProvider(ClientIdentificationProvider.class).in(Singleton.class);
		
		// Bind the RestBaseUrl class to an instance with the property for the base URL
		bind(RestBaseUrl.class).toInstance(new RestBaseUrl("", ""));

		// We can simply bind the JacksonConfigProvider here, which sets up the Jackson custom serializers.
		bind(JacksonSerializationModule.class).to(GuiberestSerializationModule.class).in(Singleton.class);
		bind(JacksonConfigProvider.class);

		bind(TestableEntityTester.class).toProvider(TestableEntityTesterProvider.class).in(Singleton.class);
		bind(GuiberestRestService.class).toProvider(GuiberestRestServiceProvider.class).in(Singleton.class);
	}

	/**
	 * Modify this function to return the appropriate properties for your application.
	 * @return
	 */
	private Properties loadProperties() {
    	Properties props = new Properties();
    	props.setProperty("ClientId-thomas", getProperty("clientid-thomas")); // Your Client Id (public)
    	props.setProperty("Secret-thomas", getProperty("secret-thomas"));// Your Client Secret (private)
    	props.setProperty("ClientId-roger", getProperty("clientid-roger")); // Your Client Id (public)
    	props.setProperty("Secret-roger", getProperty("secret-roger"));// Your Client Secret (private)
    	props.setProperty("ClientId-sean", getProperty("clientid-sean")); // Your Client Id (public)
    	props.setProperty("Secret-sean", getProperty("secret-sean"));// Your Client Secret (private)
    	props.setProperty("ClientId-irsauditer", getProperty("clientid-irsauditer")); // Your Client Id (public)
    	props.setProperty("Secret-irsauditer", getProperty("secret-irsauditer"));// Your Client Secret (private)
    	props.setProperty("ClientId-guest", getProperty("clientid-guest")); // Your Client Id (public)
    	props.setProperty("Secret-guest", getProperty("secret-guest"));// Your Client Secret (private)
    	props.setProperty("ClientId-cook", getProperty("clientid-cook")); // Your Client Id (public)
    	props.setProperty("Secret-cook", getProperty("secret-cook"));// Your Client Secret (private)
    	
    	props.setProperty("usingCAS", "false");// Not using CAS for default integration tests
    	
    	// Get Host Name from .properties file first, then attempt to dynamically get local host name
    	props.setProperty("HostName", getProperty("hostname") + ":" + getProperty("hostport")); // The Host name of the RESTful service (not the client; don't include http://)
		try {
	    	props.setProperty("HostName", java.net.InetAddress.getLocalHost().getHostName() + ":" + getProperty("hostport")); // The Host name of the RESTful service (not the client; don't include http://)
		} catch (Exception e) {}
		
		// Get REST URL from .properties file first, then attempt to dynamically generate with local host name
    	props.setProperty("RestURL", getProperty("baseurl") + ":" + getProperty("hostport") + "/");
		try {
	    	props.setProperty("RestURL", "https://" + java.net.InetAddress.getLocalHost().getHostName() + ":" + getProperty("hostport") + "/");
		} catch (Exception e) {}
		
    	// CAS Passwords
		props.setProperty("CASPassword-thomas", getProperty("cas-password-thomas"));
		props.setProperty("CASPassword-roger", getProperty("cas-password-roger"));
		props.setProperty("CASPassword-sean", getProperty("cas-password-sean"));
		props.setProperty("CASPassword-irsauditer", getProperty("cas-password-irsauditer"));
		props.setProperty("CASPassword-guest", getProperty("cas-password-guest"));
		props.setProperty("CASPassword-cook", getProperty("cas-password-cook"));
		
    	return props;
	}	
	
    private String getProperty(String resource) {
        InputStream inputStream = getClass().getResourceAsStream("/integration-test.properties");

        if (inputStream == null) {
            throw new RuntimeException();
        }

        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties.getProperty(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }    
}
