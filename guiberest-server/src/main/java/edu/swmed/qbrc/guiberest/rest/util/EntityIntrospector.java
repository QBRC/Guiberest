package edu.swmed.qbrc.guiberest.rest.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class EntityIntrospector<T> {

	private final T bean;
	
	@SuppressWarnings("rawtypes")
	private final Class annotation;
	
	@SuppressWarnings("rawtypes")
	public EntityIntrospector(final T bean, final Class annotation) {
		this.bean = bean;
		this.annotation = annotation;
	}
	
	@SuppressWarnings("unchecked")
	public T populateWith(T secondObject) {
	
		BeanInfo info = null;
		try {
			info = java.beans.Introspector.getBeanInfo(bean.getClass());
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if (info != null) {
			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
				if (pd.getWriteMethod() != null	&& pd.getReadMethod() != null) {
					if (pd.getWriteMethod().isAnnotationPresent(annotation)) {
						//System.out.println("Method1: " + pd.getWriteMethod().getName());
						//System.out.println("Method2: " + pd.getReadMethod().getName());
						try {
							pd.getWriteMethod().invoke(bean, pd.getReadMethod().invoke(secondObject));
							//System.out.println("Get Value: " + pd.getReadMethod().invoke(secondObject));
							//System.out.println("Set Value: " + pd.getReadMethod().invoke(bean));
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return (T)bean;
		}
		
		return null;
	}
}