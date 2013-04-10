package edu.swmed.qbrc.guiberest.shared.rest.jackson.deserializers;

import java.io.IOException;
import java.util.HashMap;
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
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONContainer;

@SuppressWarnings("rawtypes")
public class TableJSONContainerDeserializer extends JsonDeserializer<TableJSONContainer> {

	private final HashMap<Class<?>, TypeReference> typeRefs;
	
	public TableJSONContainerDeserializer(final HashMap<Class<?>, TypeReference> typeRefs) {
		this.typeRefs = typeRefs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TableJSONContainer deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {

		ObjectMapper mapper = (ObjectMapper) parser.getCodec();
		ObjectNode root = (ObjectNode) mapper.readTree(parser);
		
		List<Object> list = null;
		TypeReference typeRef = null;
		
		Iterator<Entry<String, JsonNode>> elementsIterator = root.getFields();
		while (elementsIterator.hasNext()) {
			Entry<String, JsonNode> element = elementsIterator.next();
			String name = element.getKey();
			
			if (name.equals("java_type")) {
				try {
					Class<?> classRef = Class.forName((String)mapper.readValue(element.getValue(), String.class));
					typeRef = typeRefs.get(classRef);
				} catch (ClassNotFoundException e) {
					// Do nothing
				}
			}
			
			else if (name.equals("data") && typeRef != null) {
				list = (List)mapper.readValue(element.getValue(), typeRef);
			}
		}
		
		TableJSONContainer tc = new TableJSONContainer(list);
		return tc;
	}
	
}