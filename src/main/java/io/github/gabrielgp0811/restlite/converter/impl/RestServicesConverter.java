/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import io.github.gabrielgp0811.restlite.annotation.RestServices;
import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.RequestHeaderTO;
import io.github.gabrielgp0811.restlite.to.RestServiceTO;
import io.github.gabrielgp0811.restlite.to.RestServicesTO;

/**
 * Class responsible for converting {@link RestServices} annotation into a
 * {@link RestServicesTO} object.
 * 
 * @author gabrielgp0811
 */
public class RestServicesConverter implements Converter<RestServices, RestServicesTO> {

	/**
	 * 
	 */
	public RestServicesConverter() {

	}

	@Override
	public RestServicesTO convert(RestServices input) throws RestException {
		if (input == null) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "input is null.");
		}

		List<RestServiceTO> services = new ArrayList<>(input.value().length);

		String url = input.url().trim();
		String protocol = input.protocol().trim();
		String host = input.host().trim();
		int port = input.port();
		String app = input.app().trim();
		String path = input.path().trim();
		RequestHeaderTO[] headers = new RequestHeaderTO[input.headers().length];

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

		for (int i = 0; i < headers.length; i++) {
			headers[i] = new RequestHeaderConverter().convert(input.headers()[i]);
		}

		for (int i = 0; i < input.value().length; i++) {
			RestServiceTO service = new RestServiceConverter().convert(input.value()[i]);

			if (input.test() || service.getUrl().trim().isEmpty()) {
				service.setUrl(url);
			}

			if (input.test() || service.getProtocol().trim().isEmpty()) {
				service.setProtocol(protocol);
			}

			if (input.test() || service.getHost().trim().isEmpty()) {
				service.setHost(host);
			}

			if (input.test() || service.getPort() <= 0) {
				service.setPort(port);
			}

			if (input.test() || service.getApp().trim().isEmpty()) {
				service.setApp(app);
			}

			if (service.getPath().trim().isEmpty()) {
				service.setPath(path);
			} else if (!path.trim().isEmpty()) {
				service.setPath(path.concat("/").concat(service.getPath()));
			}

			if (service.getMethod().trim().isEmpty()) {
				service.setMethod(input.method().trim());
			}

			if (service.getContentType().trim().isEmpty()) {
				service.setContentType(input.contentType().trim());
			}

			if (service.getCharsetRead().trim().isEmpty()) {
				service.setCharsetRead(input.charsetRead().trim());
			}

			if (service.getCharsetWrite().trim().isEmpty()) {
				service.setCharsetWrite(input.charsetWrite().trim());
			}

			if (service.getConnectTimeout() <= 0) {
				service.setConnectTimeout(input.connectTimeout());
			}

			if (service.getReadTimeout() <= 0) {
				service.setReadTimeout(input.readTimeout());
			}

			for (int j = 0; j < headers.length; j++) {
				RequestHeaderTO header = new RequestHeaderTO(headers[j].getName(), headers[j].getValue());

				for (int k = 0; k < service.getHeaders().length; k++) {
					if (header.getName().equals(service.getHeaders()[k].getName())) {
						header.setValue(service.getHeaders()[k].getValue());
					}
				}

				headers[j] = new RequestHeaderTO(header.getName(), header.getValue());
			}

			service.setHeaders(headers);

			services.add(service);
		}

		return new RestServicesTO(services);
	}

}