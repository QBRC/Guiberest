package edu.swmed.qbrc.guiberest.shared.rest.util;

import org.apache.commons.lang3.StringUtils;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserIDArray implements Serializable {

	private static final long serialVersionUID = -2928858639349553409L;

	private final List<User> list = new ArrayList<User>();
	
	public UserIDArray() {
	}
	
	public UserIDArray(String str) {
		for (String sNum : Arrays.asList(str.split(","))) {
			User ds = new User();
			ds.setId(sNum);
			list.add(ds);
		}
	}
	
	public List<User> getList() {
		return list;
	}
	
	@Override
	public String toString() {
		return StringUtils.join(list, ",");
	}
	
}
