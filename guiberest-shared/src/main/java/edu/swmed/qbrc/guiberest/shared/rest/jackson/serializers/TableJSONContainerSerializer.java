package edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers;

import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONField;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONSerializable;

@SuppressWarnings("rawtypes")
public class TableJSONContainerSerializer extends JsonSerializer<TableJSONContainer> {
	
	@SuppressWarnings("unchecked")
	@Override
	public void serialize(TableJSONContainer container, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		List<TableJSONSerializable> data = (List<TableJSONSerializable>)container.getData();
		jgen.writeStartObject();
		if (data.size() > 0) {
			jgen.writeArrayFieldStart("fields");
			for (TableJSONField field : data.get(0).getFields()) {
				jgen.writeStartObject();
				jgen.writeStringField("id", field.getFieldId());
				jgen.writeStringField("name", field.getFieldName());
				jgen.writeStringField("label", field.getFieldLabel());
				jgen.writeStringField("type", field.getFieldType());
				jgen.writeEndObject();
			}
			jgen.writeEndArray();
			jgen.writeArrayFieldStart("data");
			for (TableJSONSerializable row : data) {
				jgen.writeObject(row);
			}
			jgen.writeEndArray();
		}
		jgen.writeEndObject();
	}
	
}