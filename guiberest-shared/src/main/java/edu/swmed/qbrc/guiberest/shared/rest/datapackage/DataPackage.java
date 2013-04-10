package edu.swmed.qbrc.guiberest.shared.rest.datapackage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DataPackage {
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DataPackageClass {
		String url();
	}	

	@Retention(RetentionPolicy.RUNTIME)
	public @interface DataPackageForeignKey {
		Class<?> foreignClass();
		String keyField() default "id";
	}	

}
