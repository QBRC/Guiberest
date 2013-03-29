package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"fields"})
public class TableJSONContainer<T> {
	
	private List<T> data;
	
	public TableJSONContainer() {
	}
	
	public TableJSONContainer(List<T> data) {
		this.data = data;
	}
	
	public List<T> getData() {
		return this.data;
	}
	
	public void setData(List<T> list) {
		this.data = list;
	}
	
}  