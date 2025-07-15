/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.util.Util;

/**
 * Class responsible for converting {@link java.util.Map} into JSON string.
 * 
 * @author gabrielgp0811
 */
public class MapToJsonConverter implements Converter<Map<String, Object>, String> {

	/**
	 * 
	 */
	public MapToJsonConverter() {

	}

	@Override
	@SuppressWarnings("unchecked")
	public String convert(Map<String, Object> input) throws RestException {
		if (input == null) {
			return null;
		}

		Set<Entry<String, Object>> entrySet = input.entrySet();
		int size = entrySet.size();
		int count = 0;

		String json = "";

		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();

			json = json.concat("\"").concat(name).concat("\":");

			Object value = entry.getValue();

			if (value == null) {
				json = json.concat("null");
			} else if (Util.isNumber(value) || Util.isBoolean(value)) {
				json = json.concat(value.toString());
			} else if (Util.isDate(value)
					|| Util.isLocalDate(value)
					|| Util.isLocalDateTime(value)
					|| Util.isLocalTime(value)
					|| Util.isString(value)
					|| Util.isCharacter(value)
					|| Util.isEnum(value)) {
				json = json.concat("\"").concat(value.toString()).concat("\"");
			} else if (Util.isMap(value)) {
				json = json.concat("{");
				json = json.concat(convert((Map<String, Object>) value));
				json = json.concat("}");
			} else if (Util.isArray(value) || Util.isCollection(value)) {
				json = json.concat("[");

				if (Util.isArray(value)) {
					int length = Array.getLength(value);

					for (int i = 0; i < length; i++) {
						Object arrayValue = Array.get(value, i);

						if (Util.isNumber(arrayValue) || Util.isBoolean(arrayValue)) {
							json = json.concat(arrayValue.toString());
						} else if (Util.isMap(arrayValue)) {
							json = json.concat("{");
							json = json.concat(convert((Map<String, Object>) arrayValue));
							json = json.concat("}");
						} else {
							json = json.concat("\"").concat(arrayValue.toString()).concat("\"");
						}

						if (i + 1 < length)
							json = json.concat(",");
					}
				}

				if (Util.isCollection(value)) {

				}

				json = json.concat("]");
			}

			count++;
			if (count < size) {
				json = json.concat(",");
			}
		}

		return json;
	}

}