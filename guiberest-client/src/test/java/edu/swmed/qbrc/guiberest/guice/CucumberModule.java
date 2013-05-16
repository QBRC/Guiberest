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
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonConfigProvider;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonSerializationModule;
import edu.swmed.qbrc.jacksonate.rest.jackson.ReflectionFactory;
import edu.swmed.qbrc.jacksonate.rest.jackson.RestBaseUrl;

public class CucumberModule extends AbstractModule {

	@Override
    protected void configure() {
		
		Properties props = loadProperties();
		Names.bindProperties(binder(), props);
		
		bind(ReflectionFactory.class).in(Singleton.class);
		bind(Reflections.class).toInstance(new Reflections("edu.swmed.qbrc.lcdb.shared.domain.lcdb"));

		// Support multiple users
		bind(ClientIdentification.class).toProvider(ClientIdentificationProvider.class).in(Singleton.class);
		
		// Bind the RestBaseUrl class to an instance with the property for the base URL
		bind(RestBaseUrl.class).toInstance(new RestBaseUrl("", ""));

		// We can simply bind the JacksonConfigProvider here, which sets up the Jackson custom serializers.
		bind(JacksonSerializationModule.class).to(GuiberestSerializationModule.class).in(Singleton.class);
		bind(JacksonConfigProvider.class);

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
    	props.setProperty("HostName", getProperty("hostname")); // The Host name of the RESTful service (not the client; don't include http://)
    	props.setProperty("RestURL", getProperty("baseurl"));
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
