package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;

public class ReflectionFactory {

	private final Map<Class<?>, BeanInfoJSON> beanInfoMap;

	public ReflectionFactory() {
		beanInfoMap = new HashMap<Class<?>, BeanInfoJSON>();
	}
	
	public BeanInfoJSON getBeanInfo(Class<?> clazz) { 
		if (beanInfoMap.containsKey(clazz)) {
			return beanInfoMap.get(clazz);
		} else {
			try {
				BeanInfo info = java.beans.Introspector.getBeanInfo(clazz);
				BeanInfoJSON bjson = new BeanInfoJSON(info);
				beanInfoMap.put(clazz, bjson);
				System.out.println("Cached class in ReflectionFactory: " + clazz.getName());
				return bjson;
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
		}
		return null;
	}	

	
}