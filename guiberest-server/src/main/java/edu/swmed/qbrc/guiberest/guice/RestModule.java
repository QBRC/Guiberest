package edu.swmed.qbrc.guiberest.guice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.reflections.Reflections;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import edu.swmed.qbrc.guiberest.rest.impl.GuiberestRestServiceImpl;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.GuiberestSerializationModule;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonConfigProvider;
import edu.swmed.qbrc.jacksonate.rest.jackson.ReflectionFactory;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonSerializationModule;
import edu.swmed.qbrc.jacksonate.rest.jackson.RestBaseUrl;

public class RestModule implements Module {

	public void configure(final Binder binder) {
		
		// Must bind ReflectionFactory since our (de)Serialization providers use it.
		binder.bind(ReflectionFactory.class).in(Scopes.SINGLETON);
		binder.bind(Reflections.class).toInstance(new Reflections("edu.swmed.qbrc.guiberest.shared.domain.guiberest"));
		
		// Bind the RestBaseUrl class to an instance with the property for the base URL
		binder.bind(RestBaseUrl.class).toInstance(new RestBaseUrl(getProperty("restservice.baseurl"), getProperty("restservice.packageurl")));

		// Our main REST service implementation
		binder.bind(GuiberestRestServiceImpl.class);
		
		// We can simply bind the JacksonConfigProvider here, which sets up the Jackson custom serializers.
		binder.bind(JacksonSerializationModule.class).to(GuiberestSerializationModule.class).in(Scopes.SINGLETON);
		binder.bind(JacksonConfigProvider.class);
	}
	
	private String getProperty(final String property) {
	    InputStream inputStream = getClass().getResourceAsStream("/rest.properties");

	    if (inputStream == null) {
	        throw new RuntimeException();
	    }

	    try {
	        Properties properties = new Properties();
	        properties.load(inputStream);

	        return properties.getProperty(property);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}	
	
}