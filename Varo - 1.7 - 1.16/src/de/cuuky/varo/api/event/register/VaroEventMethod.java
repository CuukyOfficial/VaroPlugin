package de.cuuky.varo.api.event.register;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VaroEventMethod {

	/**
	 * Annonation for registering the methods
	 */

}
