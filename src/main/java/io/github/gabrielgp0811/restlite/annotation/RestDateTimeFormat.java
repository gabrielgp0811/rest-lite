/**
 * 
 */
package io.github.gabrielgp0811.restlite.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation responsible for the REST service parameter.
 * 
 * @author gabrielgp0811
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface RestDateTimeFormat {

	/**
	 * @return The pattern responsible for value's serialization/deserialization.
	 */
	String value() default "";

	/**
	 * @return The {@link java.util.Locale Locale}.
	 */
	String locale() default "";

	/**
	 * @return The {@link java.util.TimeZone TimeZone}.
	 */
	String timezone() default "";

}