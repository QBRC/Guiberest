package edu.swmed.qbrc.guiberest.shared.rest.util;

import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringArray implements Serializable {

	private static final long serialVersionUID = 1089400043582219841L;

	private final List<String> list = new ArrayList<String>();
	
	public StringArray() {
	}
	
	public StringArray(String str) {
		list.addAll(Arrays.asList(StringUtils.split(str, ",")));
	}
	
	public List<String> getList() {
		return list;
	}
	
	@Override
	public String toString() {
		return StringUtils.join(list, ",");
	}
	
}
