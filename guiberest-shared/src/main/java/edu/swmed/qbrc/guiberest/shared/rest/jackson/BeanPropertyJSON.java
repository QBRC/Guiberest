package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.writers.Callable;

public class BeanPropertyJSON {

	private Boolean JSONTransient;
	private Boolean readable;
	private Boolean writeable;
	private String JSONType;
	private String shortId;
	private PropertyDescriptor propertyDescriptor;
	@SuppressWarnings("rawtypes")
	private Callable writer;
	
	public BeanPropertyJSON(PropertyDescriptor propertyDescriptor) throws IntrospectionException {
		this.propertyDescriptor = propertyDescriptor;
	}
	
	public Boolean getJSONTransient() {
		return JSONTransient;
	}
	public void setJSONTransient(Boolean jSONTransient) {
		JSONTransient = jSONTransient;
	}
	
	public String getJSONType() {
		return JSONType;
	}
	public void setJSONType(String jSONType) {
		JSONType = jSONType;
	}

	public String getShortId() {
		return shortId;
	}
	public void setShortId(String shortId) {
		this.shortId = shortId;
	}

	public Boolean getReadable() {
		return readable;
	}

	public void setReadable(Boolean readable) {
		this.readable = readable;
	}

	public Boolean getWriteable() {
		return writeable;
	}

	public void setWriteable(Boolean writeable) {
		this.writeable = writeable;
	}

	public PropertyDescriptor getPropertyDescriptor() {
		return propertyDescriptor;
	}

	public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
	}

	@SuppressWarnings("rawtypes")
	public Callable getWriter() {
		return writer;
	}
	@SuppressWarnings("rawtypes")
	public void setWriter(Callable writer) {
		this.writer = writer;
	}
	
}