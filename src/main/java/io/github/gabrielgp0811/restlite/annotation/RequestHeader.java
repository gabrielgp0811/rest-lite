/**
 * 
 */
package io.github.gabrielgp0811.restlite.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation responsible for the REST service request header.
 * 
 * @author gabrielgp0811
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface RequestHeader {

	/**
	 * @return The request header's name (eg. "Accept").
	 */
	String name();

	/**
	 * @return The request header's value (eg. "application/json").
	 */
	String value();

}