/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;

/**
 * Main converter for parameters for Content-Type
 * <strong>application/json</strong>.
 * 
 * @author gabrielgp0811
 */
public class JsonConverter implements Converter<Collection<RestServiceParameterTO>, String> {

	/**
	 * 
	 */
	public JsonConverter() {

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
					"Parameter's type '" + parameter.getName() + "' informed is '"
							+ parameter.getValue().getClass().getName() + "'. Expected is '"
							+ parameter.getType().getName() + "'.");
		}

		Converter<Map<String, Object>, String> mapToJson = new MapToJsonConverter();

		if (input.size() == 1) {
			Converter<RestServiceParameterTO, Map<String, Object>> parameterToMap = new RestServiceParameterToMapConverter();

			return input.stream()
					.filter(Objects::nonNull)
					.filter(p -> !p.getName().trim().isEmpty())
					.map(p -> {
						try {
							return parameterToMap.convert(p);
						} catch (RestException e) {
							return null;
						}
					})
					.filter(Objects::nonNull)
					.map(m -> {
						try {
							return mapToJson.convert(m);
						} catch (RestException e) {
							return null;
						}
					})
					.filter(Objects::nonNull)
					.collect(Collectors.joining(",", "{", "}"));
		}

		Converter<Collection<RestServiceParameterTO>, Collection<Map<String, Object>>> parametersToMaps = new RestServiceParametersToMapConverter();

		return parametersToMaps.convert(input).stream()
				.map(p -> {
					try {
						return mapToJson.convert(p);
					} catch (RestException e) {
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(Collectors.joining(",", "{", "}"));
	}

}