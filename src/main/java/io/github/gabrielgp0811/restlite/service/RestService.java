/**
 * 
 */
package io.github.gabrielgp0811.restlite.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import io.github.gabrielgp0811.restlite.exception.RestException;

/**
 * Main interface for the REST Service.
 * 
 * @author gabrielgp0811
 */
public interface RestService {

	/**
	 * @param protocol The protocol for URL (https, http, etc).
	 * @param host     The host.
	 * @param file     The path of the service.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setUrl(String protocol, String host, String file);

	/**
	 * @param protocol The protocol for URL (https, http, etc).
	 * @param host     The host.
	 * @param port     The port.
	 * @param file     The path of the service.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setUrl(String protocol, String host, int port, String file);

	/**
	 * @param url The URL of the service.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setUrl(String url);

	/**
	 * @param url The URL of the service.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setUrl(URL url);

	/**
	 * @param protocol The protocol for URL (https, http, etc).
	 * @return This REST Service (Fluent interface).
	 */
	RestService setProtocol(String protocol);

	/**
	 * @param host The host.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setHost(String host);

	/**
	 * @param port The port.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setPort(int port);

	/**
	 * @param file The file.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setFile(String file);

	/**
	 * @param method The HTTP Method (GET, POST, PUT, DELETE, etc)
	 * @return This REST Service (Fluent interface).
	 */
	RestService setMethod(String method);

	/**
	 * @param contentType The content type (eg. application/x-www-form-urlencoded,
	 *                    application/json).
	 * @return This REST Service (Fluent interface).
	 */
	RestService setContentType(String contentType);

	/**
	 * @param connectTimeout The timeout in milliseconds to wait for a connection to
	 *                       service be established.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setConnectTimeout(int connectTimeout);

	/**
	 * @param readTimeout The timeout in milliseconds to read result from service
	 *                    call.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setReadTimeout(int readTimeout);

	/**
	 * @param name  The name of the request header (eg. "Accept").
	 * @param value The value of the request header (eg. "application/json").
	 * @return This REST Service (Fluent interface).
	 */
	RestService addHeader(String name, String value);

	/**
	 * @param name  The name of the request header (eg. "Accept").
	 * @param value The value of the request header (eg. "application/json").
	 * @return This REST Service (Fluent interface).
	 */
	RestService setHeader(String name, String value);

	/**
	 * @param headers The request headers.
	 * @return This REST Service (Fluent interface).
	 */
	RestService addHeaders(Map<String, String> headers);

	/**
	 * @param headers The request headers.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setHeaders(Map<String, String> headers);

	/**
	 * @param readCharsetName Charset's name used for converting output.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setReadCharset(String readCharsetName);

	/**
	 * @param readCharset Charset used for converting output.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setReadCharset(Charset readCharset);

	/**
	 * @param writeCharsetName Charset's name used for converting input.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setWriteCharset(String writeCharsetName);

	/**
	 * @param writeCharset Charset used for converting input.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setWriteCharset(Charset writeCharset);

	/**
	 * @param name  The parameter's name.
	 * @param value The parameter's value.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setParameter(String name, Object value);

	/**
	 * @param name The parameter's name.
	 * @param type The parameter's type.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setParameterType(String name, Class<?> type);

	/**
	 * @param name     The parameter's name.
	 * @param optional If parameter is optional.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setParameterOptional(String name, boolean optional);

	/**
	 * @param name    The parameter's name.
	 * @param pattern The pattern for formatting.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setParameterPattern(String name, String pattern);

	/**
	 * @param name   The parameter's name.
	 * @param locale The Locale.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setParameterLocale(String name, String locale);

	/**
	 * @param name     The parameter's name.
	 * @param timezone The Timezone.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setParameterTimezone(String name, String timezone);

	/**
	 * @param name  The parameter's name.
	 * @param value The parameter's value.
	 * @param type  The parameter's type.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setParameter(String name, Object value, Class<?> type);

	/**
	 * @param name     The parameter's name.
	 * @param type     The parameter's type.
	 * @param optional If parameter is optional.
	 * @param pattern  The pattern for formatting.
	 * @param locale   The Locale.
	 * @param timezone The TimeZone.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setParameterInfo(String name, Class<?> type, boolean optional, String pattern, String locale,
			String timezone);

	/**
	 * @param expectedStatusCode The expected HTTP Status code from service
	 *                           response.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setExpectedStatusCode(int expectedStatusCode);

	/**
	 * @param expectedStatusCodes The expected HTTP Status codes from service
	 *                            response.
	 * @return This REST Service (Fluent interface).
	 */
	RestService setExpectedStatusCodes(int[] expectedStatusCodes);

	/**
	 * @return <strong><span style="color:#7f0055">true</span></strong> if service
	 *         call has result;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 * @throws RestException Error on service call.
	 */
	boolean execute() throws RestException;

	/**
	 * @return A single object from service call.
	 * @throws RestException Error on service call.
	 */
	Object getSingleResult() throws RestException;

	/**
	 * @return The <code>java.util.List</code> containing the objects from service
	 *         call.
	 * @throws RestException Error on service call.
	 */
	List<?> getResultList() throws RestException;

	/**
	 * Same as <code>getResultList().stream()</code>.
	 * 
	 * @return The sequential stream.
	 * @throws RestException Error obtaining the sequential stream.
	 */
	default Stream<?> getResultStream() throws RestException {
		return getResultList().stream();
	}

	/**
	 * @return The HTTP Status from service call.
	 * @throws RestException Error from service call.
	 */
	int getStatusCode() throws RestException;

	/**
	 * @return The result from service call.
	 * @throws RestException Error from service call.
	 */
	String getStringResult() throws RestException;

	/**
	 * @return <strong><span style="color:#7f0055">true</span></strong> or
	 *         <strong><span style="color:#7f0055">false</span></strong>.
	 */
	default Boolean getBooleanResult() {
		try {
			return Boolean.valueOf(getStringResult());
		} catch (RestException e) {
		}

		return null;
	}

	/**
	 * @return The result as <code>java.lang.Character</code> or
	 *         <strong><span style="color:#7f0055">null</span></strong> if result is
	 *         empty.
	 */
	default Character getCharacterResult() {
		try {
			String result = getStringResult();

			if (result != null && !result.trim().isEmpty()) {
				return result.charAt(0);
			}
		} catch (RestException e) {
		}

		return null;
	}

	/**
	 * @return The result as <code>java.lang.Byte</code> or
	 *         <strong><span style="color:#7f0055">null</span></strong> if failed to
	 *         obtain and/or cast result.
	 */
	default Byte getByteResult() {
		try {
			return Byte.valueOf(getStringResult());
		} catch (RestException | NumberFormatException e) {
		}

		return null;
	}

	/**
	 * @return The result as <code>java.lang.Short</code> or
	 *         <strong><span style="color:#7f0055">null</span></strong> if failed to
	 *         obtain and/or cast result.
	 */
	default Short getShortResult() {
		try {
			return Short.valueOf(getStringResult());
		} catch (RestException | NumberFormatException e) {
		}

		return null;
	}

	/**
	 * @return The result as <code>java.lang.Integer</code> or
	 *         <strong><span style="color:#7f0055">null</span></strong> if failed to
	 *         obtain and/or cast result.
	 */
	default Integer getIntegerResult() {
		try {
			return Integer.valueOf(getStringResult());
		} catch (RestException | NumberFormatException e) {
		}

		return null;
	}

	/**
	 * @return The result as <code>java.lang.Long</code> or
	 *         <strong><span style="color:#7f0055">null</span></strong> if failed to
	 *         obtain and/or cast result.
	 */
	default Long getLongResult() {
		try {
			return Long.valueOf(getStringResult());
		} catch (RestException | NumberFormatException e) {
		}

		return null;
	}

	/**
	 * @return The result as <code>java.lang.Float</code> or
	 *         <strong><span style="color:#7f0055">null</span></strong> if failed to
	 *         obtain and/or cast result.
	 */
	default Float getFloatResult() {
		try {
			return Float.valueOf(getStringResult());
		} catch (RestException | NumberFormatException e) {
		}

		return null;
	}

	/**
	 * @return The result as <code>java.lang.Double</code> or
	 *         <strong><span style="color:#7f0055">null</span></strong> if failed to
	 *         obtain and/or cast result.
	 */
	default Double getDoubleResult() {
		try {
			return Double.valueOf(getStringResult());
		} catch (RestException | NumberFormatException e) {
		}

		return null;
	}

	/**
	 * @return The result as <code>java.math.BigInteger</code> or
	 *         <strong><span style="color:#7f0055">null</span></strong> if failed to
	 *         obtain and/or cast result.
	 */
	default BigInteger getBigIntegerResult() {
		try {
			return new BigInteger(getStringResult());
		} catch (RestException | NumberFormatException e) {
		}

		return null;
	}

	/**
	 * @return The result as <code>java.math.BigDecimal</code> or
	 *         <strong><span style="color:#7f0055">null</span></strong> if failed to
	 *         obtain and/or cast result.
	 */
	default BigDecimal getBigDecimalResult() {
		try {
			return new BigDecimal(getStringResult());
		} catch (RestException | NumberFormatException e) {
		}

		return null;
	}

}