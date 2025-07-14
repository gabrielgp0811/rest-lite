/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.net.HttpURLConnection;

import io.github.gabrielgp0811.restlite.annotation.RestService;
import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.RequestHeaderTO;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;
import io.github.gabrielgp0811.restlite.to.RestServiceTO;

/**
 * Class responsible for converting {@link RestService} annotation into a {@link RestServiceTO} object. 
 * 
 * @author gabrielgp0811
 */
public class RestServiceConverter implements Converter<RestService, RestServiceTO> {

	/**
	 * 
	 */
	public RestServiceConverter() {

	}

	@Override
	public RestServiceTO convert(RestService input) throws RestException {
		if (input == null) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "input is null.");
		}

		if (input.name().trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "Must inform REST Service name.");
		}

		RestServiceParameterTO[] parameters = new RestServiceParameterTO[input.parameters().length];

		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = new RestServiceParameterConverter().convert(input.parameters()[i]);
		}

		RequestHeaderTO[] headers = new RequestHeaderTO[input.headers().length];

		for (int i = 0; i < headers.length; i++) {
			headers[i] = new RequestHeaderConverter().convert(input.headers()[i]);
		}

		RestServiceTO result = new RestServiceTO(input.name().trim());

		String url = input.url().trim();
		String protocol = input.protocol().trim();
		String host = input.host().trim();
		int port = input.port();
		String app = input.app().trim();
		String path = input.path().trim();

		if (input.test()) {
			if (!input.testUrl().trim().isEmpty())
				url = input.testUrl().trim();
			if (!input.testProtocol().trim().isEmpty())
				protocol = input.testProtocol().trim();
			if (!input.testHost().trim().isEmpty())
				host = input.testHost().trim();
			if (input.testPort() > 0)
				port = input.testPort();
			if (!input.testApp().trim().isEmpty())
				app = input.testApp().trim();
			if (!input.testPath().trim().isEmpty())
				path = input.testPath().trim();
		}

		result.setUrl(url);
		result.setProtocol(protocol);
		result.setHost(host);
		result.setPort(port);
		result.setApp(app);
		result.setPath(path);
		result.setMethod(input.method().trim());
		result.setContentType(input.contentType().trim());
		result.setCharsetRead(input.charsetRead().trim());
		result.setCharsetWrite(input.charsetWrite().trim());
		result.setConnectTimeout(input.connectTimeout());
		result.setReadTimeout(input.readTimeout());
		result.setParameters(parameters);
		result.setHeaders(headers);
		result.setExpectedStatusCodes(input.expectedStatusCodes());

		return result;
	}

}