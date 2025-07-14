/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.github.gabrielgp0811.restlite.converter.impl.FormUrlEncodedConverter;
import io.github.gabrielgp0811.restlite.converter.impl.JsonConverter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;

/**
 * Class responsible for containing all possible converters.
 * 
 * @author gabrielgp0811
 */
public final class Converters {

	private static final Map<String, Converter<Collection<RestServiceParameterTO>, String>> CONVERTERS = new HashMap<>();

	static {
		CONVERTERS.put("application/x-www-form-urlencoded", new FormUrlEncodedConverter());
		CONVERTERS.put("application/json", new JsonConverter());
	}

	/**
	 * 
	 */
	private Converters() {

	}

	/**
	 * Obtains the converter with specified <code>key</code>.
	 * 
	 * @param key The key.
	 * @return The converter.
	 */
	public static Converter<Collection<RestServiceParameterTO>, String> getConverter(String key) {
		if (!containsKey(key)) {
			return null;
		}

		return CONVERTERS.get(key);
	}

	/**
	 * Checks if converter with specified <code>key</code> exists.
	 * 
	 * @param key The key.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if exists;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean containsKey(String key) {
		return key != null && !key.trim().isEmpty() && CONVERTERS.containsKey(key);
	}

	/**
	 * Adds the <code>converter</code> with specified <code>key</code> to the cached
	 * converters.
	 * 
	 * @param key       The key.
	 * @param converter The converter.
	 */
	public static void put(String key, Converter<Collection<RestServiceParameterTO>, String> converter) {
		CONVERTERS.put(key, converter);
	}

	/**
	 * Converts <code>input</code> using converter with specified <code>key</code>.
	 * 
	 * @param key   The key.
	 * @param input The input for the converter.
	 * @return The output or
	 *         <strong><span style="color:#7f0055">null</span></strong> if converter
	 *         does not exists.
	 * @throws RestException Error converting input.
	 */
	public static String convertValues(String key, Collection<RestServiceParameterTO> input)
			throws RestException {
		Converter<Collection<RestServiceParameterTO>, String> converter = getConverter(key);

		if (converter == null) {
			return null;
		}

		return converter.convert(input);
	}

}