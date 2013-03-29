package edu.swmed.qbrc.guiberest.shared.rest.jackson.deserializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONSerializable;

@SuppressWarnings("rawtypes")
public class TableJSONContainerDeserializer extends JsonDeserializer<TableJSONContainer> {

	@Override
	public TableJSONContainer deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {

		List<TableJSONSerializable> data = new ArrayList<TableJSONSerializable>();
		TypeReference<List<User>> typeRef = new TypeReference<List<User>>() {};
		
		ObjectMapper mapper = (ObjectMapper) parser.getCodec();
		ObjectNode root = (ObjectNode) mapper.readTree(parser);
		
		Iterator<Entry<String, JsonNode>> elementsIterator = root.getFields();
		while (elementsIterator.hasNext()) {
			Entry<String, JsonNode> element = elementsIterator.next();
			String name = element.getKey();
			if (name.equals("data")) {
				data = mapper.readValue(element.getValue(), typeRef);
			}
		}
		
		@SuppressWarnings("unchecked")
		TableJSONContainer tc = new TableJSONContainer(data);
		return tc;
	}
	
}