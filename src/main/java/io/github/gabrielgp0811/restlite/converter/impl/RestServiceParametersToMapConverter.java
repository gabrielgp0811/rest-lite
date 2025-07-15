/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;

/**
 * Class responsible for converting a collection of
 * {@link io.github.gabrielgp0811.restlite.to.RestServiceParameterTO} into a
 * collection of {@link java.util.Map}.
 * 
 * @author gabrielgp0811
 */
public class RestServiceParametersToMapConverter
		implements Converter<Collection<RestServiceParameterTO>, Collection<Map<String, Object>>> {

	/**
	 * 
	 */
	public RestServiceParametersToMapConverter() {

	}

	@Override
	public Collection<Map<String, Object>> convert(Collection<RestServiceParameterTO> input) throws RestException {
		if (input == null) {
			return null;
		}

		Converter<RestServiceParameterTO, Map<String, Object>> parameterToMap = new RestServiceParameterToMapConverter();

		return input.stream()
				.filter(Objects::nonNull)
				.filter(parameter -> !parameter.getName().trim().isEmpty())
				.map(parameter -> {
					try {
						return parameterToMap.convert(parameter);
					} catch (RestException e) {
					}

					return null;
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}