package edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers;

import java.io.IOException;
import java.util.Set;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.reflections.Reflections;
import edu.swmed.qbrc.guiberest.shared.rest.datapackage.DataPackage;
import edu.swmed.qbrc.guiberest.shared.rest.datapackage.DataPackage.DataPackageClass;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.JacksonUtils;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.ReflectionFactory;

public class DataPackageSerializer extends JsonSerializer<DataPackage> {

	private final ReflectionFactory reflectionFactory;
	private final Reflections reflections;
	
	public DataPackageSerializer(final ReflectionFactory reflectionFactory, final Reflections reflections) {
		this.reflectionFactory = reflectionFactory;
		this.reflections = reflections;
	}

	@Override
	public void serialize(DataPackage dataPackage, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(DataPackageClass.class);
		
		jgen.writeStartObject();
		jgen.writeArrayFieldStart("files");
		for (Class<?> annotated : classes) {
			DataPackageClass dp = annotated.getAnnotation(DataPackageClass.class);
			jgen.writeStartObject();
			jgen.writeStringField("url", dp.url());
			jgen.writeStringField("id", annotated.getSimpleName());
			jgen.writeObjectFieldStart("schema");
			JacksonUtils.writeJSONSchema(annotated, jgen, reflectionFactory);
			jgen.writeEndObject();
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
		jgen.writeEndObject();

	}
}