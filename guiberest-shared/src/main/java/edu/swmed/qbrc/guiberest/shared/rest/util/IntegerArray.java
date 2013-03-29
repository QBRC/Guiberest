package edu.swmed.qbrc.guiberest.shared.rest.util;

import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IntegerArray implements Serializable {

	private static final long serialVersionUID = -7391473177797295261L;
	private final List<Integer> list = new ArrayList<Integer>();
	
	public IntegerArray() {
	}
	
	public IntegerArray(String str) {
		for (String sNum : java.util.Arrays.asList(str.split(","))) {
			if (StringUtils.isNumeric(sNum)) {
				list.add(Integer.parseInt(sNum));
			}
		}
	}
	
	public List<Integer> getList() {
		return list;
	}
	
	@Override
	public String toString() {
		return StringUtils.join(list, ",");
	}
	
}
