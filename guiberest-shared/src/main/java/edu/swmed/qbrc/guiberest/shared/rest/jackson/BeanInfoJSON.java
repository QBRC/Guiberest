package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.writers.*;

public class BeanInfoJSON {

	private BeanInfo beanInfo;
	private List<BeanPropertyJSON> properties;
	
	public BeanInfoJSON(BeanInfo beanInfo) throws IntrospectionException {
		this.properties = new ArrayList<BeanPropertyJSON>();
		this.beanInfo = beanInfo;
		int index = 0;
		for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
			Class<?> beanClass = pd.getPropertyType();
			BeanPropertyJSON bp = null;
			bp = new BeanPropertyJSON(pd);
			bp.setReadable(pd.getReadMethod() != null);
			if (bp.getReadable()) {
				bp.setWriteable(pd.getWriteMethod() != null);
				bp.setJSONTransient(JacksonUtils.shouldSkip(pd.getReadMethod(), JsonIgnore.class, XmlTransient.class, Transient.class));
				bp.setJSONType(convertTypeAndSetWriter(beanClass, bp));
			}
			else
				bp.setJSONTransient(true);
			
			if (! bp.getJSONTransient())
				bp.setShortId(getId(index++));
			
			this.properties.add(bp);
		}
	}
	
	
	public BeanInfo getBeanInfo() {
		return beanInfo;
	}

	public List<BeanPropertyJSON> getProperties() {
		return this.properties;
	}
		
	
	/**
	 * Returns an alphabetical field id, beginning with "A" if index is 0.  When "Z" is reached,
	 * "AA" will be the next field.  When "ZZ" is reached, "AAA" will be the next field, and so on.
	 * @param index
	 * @return
	 */
	private String getId(Integer index) {
		int alpha = index;
		String out;
		if (index >= 26) {
			alpha = index % 26;
			out = Character.toString(((char)(alpha+65)));
			int loop = index / 26 + 1;
			for (int i=1; i < loop; i++) {
				out += out;
			}
		} else {
			out = Character.toString(((char)(alpha+65)));
		}
		return out;
	}
	
	/**
	 * Converts Java property types to expected types in the JSON Table Schema.
	 * Also sets the writer for the property, which is used by the serializer to 
	 * write the object out to JSON.  It's much more efficient to explicitly create
	 * a writer that uses the correct "write" method (writeBoolean, writeNumber,
	 * writeString, etc.) instead of simply using "writeObject".
	 * @param propertyType
	 * @return
	 */
	private String convertTypeAndSetWriter(Class<?> propertyType, BeanPropertyJSON bp) {
		if (propertyType.equals(String.class)) {
			bp.setWriter(new StringWriter());
			return "string";
		}
		else if (propertyType.equals(Integer.class)) {
			bp.setWriter(new IntegerWriter());
			return "integer";
		}
		else if (propertyType.equals(Long.class)) {
			bp.setWriter(new LongWriter());
			return "integer";
		}
		else if (propertyType.equals(Short.class)) {
			bp.setWriter(new ShortWriter());
			return "integer";
		}
		else if (propertyType.equals(Boolean.class)) {
			bp.setWriter(new BooleanWriter());
			return "boolean";
		}
		else if (propertyType.equals(Float.class)) {
			bp.setWriter(new FloatWriter());
			return "number";
		}
		else if (propertyType.equals(Double.class)) {
			bp.setWriter(new DoubleWriter());
			return "number";
		}
		else {
			bp.setWriter(new ObjectWriter());
			return propertyType.getName();
		}
	}
	

}