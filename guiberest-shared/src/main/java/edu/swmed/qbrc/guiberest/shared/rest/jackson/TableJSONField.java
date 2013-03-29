package edu.swmed.qbrc.guiberest.shared.rest.jackson;

public class TableJSONField {

	public static class JSONFieldTypes {
		public static final String STRING = "string";
		public static final String NUMBER = "number";
		public static final String INTEGER = "integer";
		public static final String DATE = "date";
		public static final String TIME = "time";
		public static final String DATETIME = "date-time";
		public static final String BOOLEAN = "boolean";
		public static final String BINARY = "binary";
	}
	
	private String fieldName;
	private String fieldId;
	private String fieldLabel;
	private String fieldType;

	public TableJSONField(String fieldId, String fieldType, String fieldName, String fieldLabel) {
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.fieldLabel = fieldLabel;
		this.setFieldType(fieldType);
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
}