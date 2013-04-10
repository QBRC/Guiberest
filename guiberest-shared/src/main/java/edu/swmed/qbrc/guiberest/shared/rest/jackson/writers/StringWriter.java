package edu.swmed.qbrc.guiberest.shared.rest.jackson.writers;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

public class StringWriter implements Callable<String> {
	@Override
	public void call(Object param, JsonGenerator jgen) throws JsonGenerationException, IOException {
		if (param != null) 
			jgen.writeString((String)param);
		else
			jgen.writeNull();
	}
}
