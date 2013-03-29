package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.ObjectMapper;

@Provider
@Produces("application/json")
public class JacksonConfigProvider implements ContextResolver<ObjectMapper> {

	private ObjectMapper objectMapper;
	
	public JacksonConfigProvider() throws Exception {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JacksonSerializationModule());
	}
	
	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		return objectMapper;
	}
	
}