package edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;

public class UserExpressionSerializer extends JsonSerializer<User> {
	
	@Override
	public void serialize(User row, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartArray();
		jgen.writeString(row.getId().toString());
		jgen.writeString(row.getPassword());
		jgen.writeString(row.getSecret());
		jgen.writeEndArray();
	}
	
}