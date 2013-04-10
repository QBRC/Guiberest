package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import java.io.IOException;
import java.lang.reflect.Method;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

import edu.swmed.qbrc.guiberest.shared.rest.datapackage.DataPackage.DataPackageForeignKey;

public class JacksonUtils {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Boolean shouldSkip(Method method, Class... classes) {
		for (Class clazz : classes) {
			if (method.getName().equals("getClass"))
				return true;
			if (method.isAnnotationPresent(clazz))
				return true;
		}
		return false;
	}
	
	public static void writeJSONSchema(Class<?> dataClass, JsonGenerator jgen, ReflectionFactory reflectionFactory) throws JsonGenerationException, IOException {
		jgen.writeStringField("java_type", dataClass.getName());
		jgen.writeArrayFieldStart("fields");
		
		BeanInfoJSON info = reflectionFactory.getBeanInfo(dataClass);
		if (info != null) {
			for (BeanPropertyJSON pd : info.getProperties()) {
				if (pd.getReadable() && !pd.getJSONTransient()) {
					jgen.writeStartObject();
					jgen.writeStringField("id", pd.getPropertyDescriptor().getName());
					jgen.writeStringField("label", pd.getPropertyDescriptor().getDisplayName());
					jgen.writeStringField("type", pd.getJSONType());
					if (pd.getPropertyDescriptor().getReadMethod().isAnnotationPresent(DataPackageForeignKey.class)) {
						DataPackageForeignKey dpfk = pd.getPropertyDescriptor().getReadMethod().getAnnotation(DataPackageForeignKey.class);
						jgen.writeObjectFieldStart("foreign key");
						jgen.writeStringField("pkg", "/datapackage.json");
						jgen.writeStringField("file", dpfk.foreignClass().getSimpleName());
						jgen.writeStringField("id", dpfk.keyField());
						jgen.writeEndObject();
					}
					jgen.writeEndObject();
				}
			}
		}
		
		jgen.writeEndArray();
		
	}
	
}