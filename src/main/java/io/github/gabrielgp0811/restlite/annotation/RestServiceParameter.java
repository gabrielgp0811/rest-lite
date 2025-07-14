/**
 * 
 */
package io.github.gabrielgp0811.restlite.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.github.gabrielgp0811.jsonlite.annotation.JsonPattern;

/**
 * Annotation responsible for the REST service parameter.
 * 
 * @author gabrielgp0811
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface RestServiceParameter {

	/**
	 * @return The parameter's name.
	 */
	String name();

	/**
	 * @return The parameter's type.
	 */
	Class<?> type();

	/**
	 * @return The date/time format.
	 */
	JsonPattern dateTimeFormat() default @JsonPattern;

	/**
	 * @return <strong><span style="color:#7f0055">true</span></strong> if optional;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	boolean optional() default false;

}