package de.cuuky.varo.serialize.identifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VaroSerializeField {

	String path() default "PATH";
	String enumValue() default "ENUM";
	Class<? extends VaroSerializeable> arrayClass() default NullClass.class;	

}
