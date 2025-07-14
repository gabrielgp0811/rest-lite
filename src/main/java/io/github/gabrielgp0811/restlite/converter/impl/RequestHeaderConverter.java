/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.net.HttpURLConnection;

import io.github.gabrielgp0811.restlite.annotation.RequestHeader;
import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.RequestHeaderTO;

/**
 * Class responsible for converting {@link RequestHeader} annotation into a
 * {@link RequestHeaderTO} object.
 * 
 * @author gabrielgp0811
 */
public class RequestHeaderConverter implements Converter<RequestHeader, RequestHeaderTO> {

	/**
	 * 
	 */
	public RequestHeaderConverter() {

	}

	@Override
	public RequestHeaderTO convert(RequestHeader input) throws RestException {
		if (input == null) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "input is null.");
		}

		if (input.name().trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "Must inform request header's name.");
		}

		return new RequestHeaderTO(input.name(), input.value());
	}

}