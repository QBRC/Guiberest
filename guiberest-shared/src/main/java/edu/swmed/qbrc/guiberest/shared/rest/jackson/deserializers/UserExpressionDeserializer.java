package edu.swmed.qbrc.guiberest.shared.rest.jackson.deserializers;

import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;

public class UserExpressionDeserializer extends JsonDeserializer<User> {

	@Override
	public User deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		User user = new User();
		user.setId(node.get(0).getTextValue());
		user.setPassword(node.get(1).getTextValue());
		user.setSecret(node.get(2).getTextValue());
		return user;
	}
	
}