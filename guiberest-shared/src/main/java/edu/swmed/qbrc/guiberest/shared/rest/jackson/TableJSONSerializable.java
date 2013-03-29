package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import java.util.List;

public abstract class TableJSONSerializable {
	public abstract List<TableJSONField> getFields();
}