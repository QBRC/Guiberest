package edu.swmed.qbrc.guiberest.shared.rest.jackson.writers;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

public class DoubleWriter implements Callable<Double> {
	@Override
	public void call(Object param, JsonGenerator jgen) throws JsonGenerationException, IOException {
		if (param != null) 
			jgen.writeNumber((Double)param);
		else
			jgen.writeNull();
	}
}