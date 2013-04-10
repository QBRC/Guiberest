package edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers;

import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.JacksonUtils;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.ReflectionFactory;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONContainer;

@SuppressWarnings("rawtypes")
public class TableJSONContainerSerializer extends JsonSerializer<TableJSONContainer> {

	private final ReflectionFactory reflectionFactory;
	
	public TableJSONContainerSerializer(final ReflectionFactory reflectionFactory) {
		this.reflectionFactory = reflectionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void serialize(TableJSONContainer container, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		List<Object> data = (List<Object>)container.getData();
		jgen.writeStartObject();
		if (data.size() > 0) {
			
			JacksonUtils.writeJSONSchema(data.get(0).getClass(), jgen, reflectionFactory);
			
			jgen.writeArrayFieldStart("data");
			for (Object row : data) {
				jgen.writeObject(row);
			}
			jgen.writeEndArray();
		}
		jgen.writeEndObject();
	}
	
}