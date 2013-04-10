package edu.swmed.qbrc.guiberest.guice;

import org.reflections.Reflections;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import edu.swmed.qbrc.guiberest.rest.impl.GuiberestRestServiceImpl;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.JacksonConfigProvider;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.ReflectionFactory;

public class RestModule implements Module {

	public void configure(final Binder binder) {
		
		// Must bind ReflectionFactory since our (de)Serialization providers use it.
		binder.bind(ReflectionFactory.class).in(Scopes.SINGLETON);
		binder.bind(Reflections.class).toInstance(new Reflections("edu.swmed.qbrc.guiberest.shared.domain.guiberest"));
		
		// Our main REST service implementation
		binder.bind(GuiberestRestServiceImpl.class);
		
		// We can simply bind the JacksonConfigProvider here, which sets up the Jackson custom serializers.
		binder.bind(JacksonConfigProvider.class);
	}
	
}