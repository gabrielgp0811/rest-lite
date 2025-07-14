/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.net.HttpURLConnection;

import io.github.gabrielgp0811.restlite.annotation.RestServiceParameter;
import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.DateTimeFormatTO;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;

/**
 * Class responsible for converting {@link RestServiceParameter} annotation into
 * a {@link RestServiceParameterTO} object.
 * 
 * @author gabrielgp0811
 */
public class RestServiceParameterConverter implements Converter<RestServiceParameter, RestServiceParameterTO> {

	/**
	 * 
	 */
	public RestServiceParameterConverter() {

	}

	@Override
	public RestServiceParameterTO convert(RestServiceParameter input) throws RestException {
		if (input == null) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "input is null.");
		}

		if (input.name().trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Must inform REST Service parameter's name.");
		}

		return new RestServiceParameterTO(input.name(), input.type(), new DateTimeFormatTO(input.dateTimeFormat().value(),
        		input.dateTimeFormat().locale(), input.dateTimeFormat().timezone()),
				input.optional());
	}

}