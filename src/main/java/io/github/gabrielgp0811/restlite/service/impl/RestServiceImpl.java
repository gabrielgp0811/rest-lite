/**
 * 
 */
package io.github.gabrielgp0811.restlite.service.impl;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.gabrielgp0811.restlite.converter.Converters;
import io.github.gabrielgp0811.restlite.converter.impl.JsonToObjectConverter;
import io.github.gabrielgp0811.restlite.converter.impl.ValueConverter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.service.RestService;
import io.github.gabrielgp0811.restlite.to.DateTimeFormatTO;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;

/**
 * Main implementation of interface {@link RestService}.
 * 
 * @author gabrielgp0811
 */
public class RestServiceImpl implements RestService {

	private String protocol = null;

	private String host = null;

	private int port = -1;

	private String file = null;

	private String method = null;

	private String contentType = null;

	private Charset charsetRead = null;

	private Charset charsetWrite = null;

	private int connectTimeout = 0;

	private int readTimeout = 0;

	private Map<String, String> headers = null;

	private List<RestServiceParameterTO> parameters = null;

	private int[] expectedStatusCodes = null;

	private String result = null;

	private int statusCode = -1;

	/**
	 * 
	 */
	public RestServiceImpl() {
		headers = new HashMap<>();
		parameters = new ArrayList<>();
	}

	@Override
	public RestService setUrl(String protocol, String host, String file) {
		return setUrl(protocol, host, -1, file);
	}

	@Override
	public RestService setUrl(String protocol, String host, int port, String file) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.file = file;

		return this;
	}

	@Override
	public RestService setUrl(String url) {
		try {
			setUrl(new URL(url));
		} catch (MalformedURLException e) {
		}

		return this;
	}

	@Override
	public RestService setUrl(URL url) {
		return setUrl(url.getProtocol(), url.getHost(), url.getPort(), url.getFile());
	}

	@Override
	public RestService setProtocol(String protocol) {
		this.protocol = protocol;
		return this;
	}

	@Override
	public RestService setHost(String host) {
		this.host = host;
		return this;
	}

	@Override
	public RestService setPort(int port) {
		this.port = port;
		return this;
	}

	@Override
	public RestService setFile(String file) {
		this.file = file;
		return this;
	}

	@Override
	public RestService setMethod(String method) {
		this.method = method;
		return this;
	}

	@Override
	public RestService setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	@Override
	public RestService setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}

	@Override
	public RestService setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}

	@Override
	public RestService addHeader(String name, String value) {
		if (name != null && !name.trim().isEmpty() && value != null && !headers.containsKey(name)) {
			headers.put(name, value);
		}

		return this;
	}

	@Override
	public RestService setHeader(String name, String value) {
		if (name != null && !name.trim().isEmpty() && value != null) {
			headers.put(name, value);
		}

		return this;
	}

	@Override
	public RestService addHeaders(Map<String, String> headers) {
		if (headers != null) {
			headers.forEach(this::addHeader);
		}

		return this;
	}

	@Override
	public RestService setHeaders(Map<String, String> headers) {
		if (headers != null) {
			this.headers = headers;
		}

		return this;
	}

	@Override
	public RestService setReadCharset(String readCharsetName) {
		try {
			charsetRead = Charset.forName(readCharsetName);
		} catch (Exception e) {
		}
		return this;
	}

	@Override
	public RestService setReadCharset(Charset readCharset) {
		this.charsetRead = readCharset;
		return this;
	}

	@Override
	public RestService setWriteCharset(String writeCharsetName) {
		try {
			charsetWrite = Charset.forName(writeCharsetName);
		} catch (Exception e) {
		}
		return this;
	}

	@Override
	public RestService setWriteCharset(Charset writeCharset) {
		this.charsetWrite = writeCharset;
		return this;
	}

	@Override
	public RestService setParameter(String name, Object value) {
		if (value == null)
			return this;

		return setParameter(name, value, value.getClass());
	}

	@Override
	public RestService setParameterType(String name, Class<?> type) {
		RestServiceParameterTO to = getParameter(name);

		if (to.getName().isEmpty()) {
			parameters.add(to);

			to.setName(name);
		}

		to.setType(type);

		return this;
	}

	@Override
	public RestService setParameterOptional(String name, boolean optional) {
		RestServiceParameterTO to = getParameter(name);

		if (to.getName().isEmpty()) {
			parameters.add(to);

			to.setName(name);
		}

		to.setOptional(optional);

		return this;
	}

	@Override
	public RestService setParameterPattern(String name, String pattern) {
		RestServiceParameterTO to = getParameter(name);

		if (to.getName().isEmpty()) {
			parameters.add(to);

			to.setName(name);
		}

		to.getDateTimeFormat().setPattern(pattern);;

		return this;
	}

	@Override
	public RestService setParameterLocale(String name, String locale) {
		RestServiceParameterTO to = getParameter(name);

		if (to.getName().isEmpty()) {
			parameters.add(to);

			to.setName(name);
		}

		to.getDateTimeFormat().setLocale(locale);

		return this;
	}

	@Override
	public RestService setParameterTimezone(String name, String timezone) {
		RestServiceParameterTO to = getParameter(name);

		if (to.getName().isEmpty()) {
			parameters.add(to);

			to.setName(name);
		}

		to.getDateTimeFormat().setTimezone(timezone);

		return this;
	}

	@Override
	public RestService setParameter(String name, Object value, Class<?> type) {
		RestServiceParameterTO to = getParameter(name);

		if (to.getName().isEmpty()) {
			parameters.add(to);

			to.setName(name);
		}

		to.setValue(value);
		to.setType(type);

		return this;
	}

	@Override
	public RestService setParameterInfo(String name, Class<?> type, boolean optional, String pattern, String locale,
			String timezone) {
		RestServiceParameterTO to = getParameter(name);

		if (to.getName().isEmpty()) {
			to.setName(name);
			
			parameters.add(to);
		}

		to.setType(type);
		to.setOptional(optional);
		to.setDateTimeFormat(new DateTimeFormatTO(pattern, locale, timezone));

		return this;
	}

	@Override
	public RestService setExpectedStatusCode(int expectedStatusCode) {
		return setExpectedStatusCodes(new int[] { expectedStatusCode });
	}

	@Override
	public RestService setExpectedStatusCodes(int[] expectedStatusCodes) {
		this.expectedStatusCodes = expectedStatusCodes;
		return this;
	}

	@Override
	public boolean execute() throws RestException {
		result = execute0(protocol, host, port, file, method, contentType, connectTimeout, readTimeout, headers,
				charsetRead, charsetWrite, parameters, expectedStatusCodes);

		return result != null && !result.trim().isEmpty();
	}

	@Override
	public int getStatusCode() throws RestException {
		execute();

		return statusCode;
	}

	@Override
	public String getStringResult() throws RestException {
		execute();

		return result;
	}

	@Override
	public Object getSingleResult() throws RestException {
		List<?> result = getResultList();

		if (result.isEmpty()) {
			throw new RestException(HTTP_INTERNAL_ERROR, "REST Service has no result.");
		}

		if (result.size() > 1) {
			throw new RestException(HTTP_INTERNAL_ERROR, "REST Service has more than one result.");
		}

		return result.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<?> getResultList() throws RestException {
		if (!execute()) {
			throw new RestException(HTTP_INTERNAL_ERROR, "REST Service has no result.");
		}

		List<Object> resultList = new ArrayList<>();

		Object result = new JsonToObjectConverter().convert(this.result);

		if (result != null && result instanceof Collection) {
			resultList = (List<Object>) result;
		}

		if (result != null && Map.class.isAssignableFrom(result.getClass())) {
			resultList.add(result);
		}

		return resultList;
	}

	private String execute0(String protocol, String host, int port, String file, String method, String contentType,
			int connectTimeout, int readTimeout, Map<String, String> headers, Charset charsetRead, Charset charsetWrite,
			List<RestServiceParameterTO> parameters, int[] expectedStatusCodes) throws RestException {
		if (protocol == null || protocol.trim().isEmpty()) {
			throw new RestException(HTTP_BAD_REQUEST, "Must inform URL protocol.");
		}

		if (host == null || host.trim().isEmpty()) {
			throw new RestException(HTTP_BAD_REQUEST, "Must inform URL host.");
		}

		if (file == null) {
			file = "";
		}

		if (!file.startsWith("/")) {
			file = "/".concat(file);
		}

		if (method == null || method.trim().isEmpty()) {
			method = "GET";
		}

		if (connectTimeout < 0) {
			connectTimeout = 0;
		}

		if (readTimeout < 0) {
			readTimeout = 0;
		}

		if (headers == null) {
			headers = new HashMap<>();
		}

		if (expectedStatusCodes == null) {
			expectedStatusCodes = new int[0];
		}

		if (!file.trim().isEmpty()) {
			for (int i = 0; i < parameters.size(); i++) {
				RestServiceParameterTO param = parameters.get(i);
				if (file.contains(":".concat(param.getName()))) {
					file = file.replaceAll(":".concat(param.getName()), new ValueConverter().convert(param));

					parameters.remove(i);
					i--;
				}
			}
		}

		String body = "";
		if ("GET".equalsIgnoreCase(method)) {
			if (!parameters.isEmpty())
				file = file.concat("?")
						.concat(Converters.convertValues("application/x-www-form-urlencoded", parameters));
		} else {
			String contentType0 = contentType;

			if (contentType0 == null || contentType0.trim().isEmpty()) {
				contentType0 = "application/x-www-form-urlencoded";
			}

			if (Converters.containsKey(contentType0)) {
				body = Converters.convertValues(contentType0, parameters);
			}
		}

		URL url = null;
		try {
			if (port > 0) {
				url = new URL(protocol, host, port, file);
			} else {
				url = new URL(protocol, host, file);
			}
		} catch (MalformedURLException e) {
			throw new RestException(HTTP_INTERNAL_ERROR, "Error building URL.", e);
		}

		if (charsetWrite == null) {
			charsetWrite = Charset.defaultCharset();

			if (charsetWrite == null) {
				charsetWrite = StandardCharsets.UTF_8;
			}
		}

		if (charsetRead == null) {
			charsetRead = Charset.defaultCharset();

			if (charsetRead == null) {
				charsetRead = StandardCharsets.UTF_8;
			}
		}

		String result = "";

		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod(method);
			connection.setConnectTimeout(connectTimeout);
			connection.setReadTimeout(readTimeout);
			connection.setDoInput(true);
			connection.setDoOutput(!body.trim().isEmpty());

			headers.entrySet().stream()
					.filter(entry -> !entry.getKey().trim().isEmpty() && !entry.getValue().trim().isEmpty())
					.forEach(entry -> connection.addRequestProperty(entry.getKey(), entry.getValue()));

			if (!body.trim().isEmpty()) {
				if (contentType != null && !contentType.trim().isEmpty()) {
					connection.setRequestProperty("Content-Type", contentType);
				}

				connection.setRequestProperty("Content-Length", Integer.toString(body.getBytes(charsetWrite).length));

				try (OutputStream writer = connection.getOutputStream()) {
					writer.write(body.getBytes(charsetWrite));
				} catch (IOException e) {
					throw new RestException(HTTP_INTERNAL_ERROR, "Error sending data to '" + url + "'.", e);
				}
			}

			statusCode = connection.getResponseCode();

			long contentLength = connection.getContentLengthLong();

			boolean isChunked = "chunked".equalsIgnoreCase(connection.getHeaderField("Transfer-Encoding"));

			if (isChunked)
				contentLength = 256L;

			if (statusCode > -1 && statusCode != HTTP_OK
					&& !Arrays.stream(expectedStatusCodes).filter(code -> code == statusCode).findAny().isPresent()) {
				String errorMessage = "Error with request.";

				if (contentLength > 0) {
					try (InputStream input = connection.getErrorStream()) {
						int read = -1;
						errorMessage = "";
						do {
							byte[] b = new byte[(int) contentLength];

							read = input.read(b);

							if (read > 0)
								errorMessage = errorMessage.concat(new String(b, charsetRead)).trim();
						} while (read > 0);
					} catch (IOException e) {
					}
				}

				throw new RestException(statusCode, errorMessage);
			}

			if (contentLength > 0) {
				try (InputStream input = connection.getInputStream()) {
					int read = -1;
					do {
						byte[] b = new byte[(int) contentLength];

						read = input.read(b);

						if (read > 0)
							result = result.concat(new String(b, charsetRead)).trim();
					} while (read > 0);
				} catch (IOException e) {
					throw new RestException(HTTP_INTERNAL_ERROR, "Error receiving data from '" + url + "'.", e);
				}
			}
		} catch (MalformedURLException e) {
			throw new RestException(HTTP_INTERNAL_ERROR, "Error refactoring URL '" + url + "' with request body.", e);
		} catch (ProtocolException e) {
			throw new RestException(HTTP_INTERNAL_ERROR,
					"Error setting HTTP Method '" + method + "' for URL '" + url + "'.", e);
		} catch (SocketTimeoutException e) {
			throw new RestException(HTTP_CLIENT_TIMEOUT, "Timeout connecting or obtaining result from '" + url + "'.",
					e);
		} catch (IOException e) {
			throw new RestException(HTTP_INTERNAL_ERROR, "Error on URL '" + url + "'.", e);
		}

		return result;
	}

	/**
	 * @param name The name of the parameter.
	 * @return The REST service parameter object.
	 */
	private RestServiceParameterTO getParameter(String name) {
		return parameters.stream().filter(param -> param.getName().equals(name)).findFirst()
				.orElseGet(RestServiceParameterTO::new);
	}

}