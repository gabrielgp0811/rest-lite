/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter;

import io.github.gabrielgp0811.restlite.exception.RestException;

/**
 * Interface responsible for converting objects.
 * 
 * @param <I> Generic type for input.
 * @param <O> Generic type for output.
 * @author gabrielgp0811
 */
public interface Converter<I, O> {

	/**
	 * Method responsible for converting <code>input</code> into an output value.
	 * 
	 * @param input The input.
	 * @return The value converted.
	 * @throws RestException Error converting value.
	 */
	O convert(I input) throws RestException;

}