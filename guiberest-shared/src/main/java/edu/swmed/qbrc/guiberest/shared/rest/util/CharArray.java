package edu.swmed.qbrc.guiberest.shared.rest.util;

import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CharArray implements Serializable {

	private static final long serialVersionUID = 1089400043582219841L;

	private final List<Character> list = new ArrayList<Character>();
	
	public CharArray() {
	}
	
	public CharArray(String str) {
		for (String sNum : java.util.Arrays.asList(str.split(","))) {
			list.add(sNum.charAt(0));
		}
	}
	
	public List<Character> getList() {
		return list;
	}
	
	@Override
	public String toString() {
		return StringUtils.join(list, ",");
	}
	
}
