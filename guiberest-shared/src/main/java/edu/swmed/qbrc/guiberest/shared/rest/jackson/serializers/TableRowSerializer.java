package edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.BeanInfoJSON;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.BeanPropertyJSON;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.ReflectionFactory;

public class TableRowSerializer<T> extends JsonSerializer<T> {
	
	private final ReflectionFactory reflectionFactory;
	
	public TableRowSerializer(final ReflectionFactory reflectionFactory) {
		this.reflectionFactory = reflectionFactory;
	}
	
	@Override
	public void serialize(T row, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartArray();
		
		BeanInfoJSON info = reflectionFactory.getBeanInfo(row.getClass());
		if (info != null) {
			for (BeanPropertyJSON pd : info.getProperties()) {
				if (pd.getReadable() && !pd.getJSONTransient()) {
					try {
						pd.getWriter().call(pd.getPropertyDescriptor().getReadMethod().invoke(row), jgen);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}		
		
		jgen.writeEndArray();
	}
		
}
