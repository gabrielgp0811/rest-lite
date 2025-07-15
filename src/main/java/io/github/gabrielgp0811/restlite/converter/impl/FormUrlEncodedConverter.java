/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;
import io.github.gabrielgp0811.restlite.util.Util;

/**
 * Main converter for Content-Type <strong>application/x-www-form-urlencoded</strong>.
 * 
 * @author gabrielgp0811
 */
public class FormUrlEncodedConverter implements Converter<Collection<RestServiceParameterTO>, String> {

	/**
	 * 
	 */
	public FormUrlEncodedConverter() {

	}

	@Override
	public String convert(Collection<RestServiceParameterTO> input) throws RestException {
		if (input == null) {
			input = new ArrayList<>();
		}

		RestServiceParameterTO parameter = input.stream()
				.filter(Objects::nonNull)
				.filter(p -> !p.getName().trim().isEmpty())
				.filter(p -> p.getValue() == null && !p.isOptional())
				.findFirst()
				.orElseGet(RestServiceParameterTO::new);

		if (!parameter.getName().trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Must inform parameter '" + parameter.getName() + "'.");
		}

		parameter = input.stream()
				.filter(Objects::nonNull)
				.filter(p -> !p.getName().trim().isEmpty())
				.filter(p -> p.getValue() != null)
				.filter(p -> !p.getType().getName().equals(p.getValue().getClass().getName()))
				.findFirst()
				.orElseGet(RestServiceParameterTO::new);

		if (!parameter.getName().trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Parameter's type '" + parameter.getName() + "' informed is '" + parameter.getValue().getClass().getName()
							+ "'. Expected is '" + parameter.getType().getName() + "'.");
		}

		return input.stream()
				.filter(Objects::nonNull)
				.filter(p -> !p.getName().trim().isEmpty())
				.map(this::getParameter)
				.collect(Collectors.joining("&"));
	}

	/**
	 * @param parameter The rest service parameter object.
	 * @return The parameter converted.
	 */
	private String getParameter(RestServiceParameterTO parameter) {
		String name = parameter.getName();
		Object value = parameter.getValue();

		if (Util.isArray(value)) {
			try {
				Object[] array = new Object[Array.getLength(value)];

				for (int i = 0; i < array.length; i++) {
					array[i] = Array.get(value, i);
				}

				return Arrays.stream(array)
						.map(elem -> new RestServiceParameterTO(name, elem, parameter.getDateTimeFormat()))
						.map(this::getParameter)
						.collect(Collectors.joining("&"));
			} catch (IllegalArgumentException e) {
			}
		} else if (Util.isCollection(value)) {
			try {
				Collection<?> list = (Collection<?>) value;

				return list.stream()
						.map(elem -> new RestServiceParameterTO(name, elem, parameter.getDateTimeFormat()))
						.map(this::getParameter)
						.collect(Collectors.joining("&"));
			} catch (ClassCastException e) {
			}
		}

		try {
			value = new ValueConverter().convert(parameter);
		} catch (RestException e) {
		}

		try {
			value = URLEncoder.encode(value.toString().trim(), StandardCharsets.UTF_8.displayName());
		} catch (UnsupportedEncodingException e) {
		}

		return name.trim().concat("=").concat(value.toString().trim());
	}

}