package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.ObjectMapper;
import org.reflections.Reflections;
import com.google.inject.Inject;

@Provider
@Produces("application/json")
public class JacksonConfigProvider implements ContextResolver<ObjectMapper> {

	private ObjectMapper objectMapper;
	
	@Inject
	public JacksonConfigProvider(final ReflectionFactory reflectionFactory, final Reflections reflections) {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JacksonSerializationModule(reflectionFactory, reflections));
	}
	
	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		return objectMapper;
	}
	
}