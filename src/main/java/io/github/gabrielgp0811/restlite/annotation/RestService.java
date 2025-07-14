/**
 * 
 */
package io.github.gabrielgp0811.restlite.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation responsible for the REST service informations.
 * 
 * @author gabrielgp0811
 */
@Documented
@Retention(RUNTIME)
@Target({ ANNOTATION_TYPE, TYPE })
@Repeatable(RestServices.class)
public @interface RestService {

	/**
	 * The REST service name used to obtain the informations required to create a
	 * {@link io.github.gabrielgp0811.restlite.service.RestService
	 * IRestService} object.
	 * 
	 * @return The REST service name.
	 */
	String name();

	/**
	 * @return The REST service URL.
	 */
	String url() default "";

	/**
	 * @return The REST service protocol to build the URL (https, http, etc).
	 */
	String protocol() default "";

	/**
	 * @return The REST service host to build the URL.
	 */
	String host() default "";

	/**
	 * @return The REST service port to build the URL.
	 */
	int port() default 0;

	/**
	 * @return The REST service app's name containing all the services. Used to
	 *         build the URL.
	 */
	String app() default "";

	/**
	 * @return The REST service path to build the URL.
	 */
	String path() default "";

	/**
	 * Set flag to <strong><span style="color:#7f0055">true</span></strong> to use
	 * {@link #testUrl()} or {@link #testProtocol()}, {@link #testHost()},
	 * {@link #testPort()}, {@link #testApp()} and {@link #testPath()}.
	 * 
	 * @return <strong><span style="color:#7f0055">true</span></strong> for enabling
	 *         tests; <strong><span style="color:#7f0055">false</span></strong>
	 *         otherwise.
	 */
	boolean test() default false;

	/**
	 * @return The REST service URL used for testing.
	 */
	String testUrl() default "";

	/**
	 * @return The REST service protocol to build the URL for testing (https, http,
	 *         etc).
	 */
	String testProtocol() default "";

	/**
	 * @return The REST service host to build the URL for testing.
	 */
	String testHost() default "";

	/**
	 * @return The REST service port to build the URL for testing.
	 */
	int testPort() default 0;

	/**
	 * @return The REST service app's name containing all the services. Used to
	 *         build the URL for testing.
	 */
	String testApp() default "";

	/**
	 * @return The REST service path to build the URL for testing.
	 */
	String testPath() default "";

	/**
	 * @return The HTTP method (GET, POST, PUT, DELETE, etc).
	 */
	String method() default "GET";

	/**
	 * @return The content type (eg. application/x-www-form-urlencoded,
	 *         application/json, etc).
	 */
	String contentType() default "";

	/**
	 * @return The Charset for the REST service response.
	 */
	String charsetRead() default "UTF-8";

	/**
	 * @return The Charset for the REST service input.
	 */
	String charsetWrite() default "UTF-8";

	/**
	 * Timeout in milliseconds to connect to REST service. A zero (0) value
	 * indicates an infinite timeout.
	 * 
	 * @return Timeout in milliseconds
	 */
	int connectTimeout() default 0;

	/**
	 * Timeout in milliseconds to wait for REST service response. A zero (0) value
	 * indicates an infinite timeout.
	 * 
	 * @return Timeout in milliseconds
	 */
	int readTimeout() default 0;

	/**
	 * @return The request headers (eg. "Accept", etc).
	 */
	RequestHeader[] headers() default {};

	/**
	 * @return The REST service input.
	 */
	RestServiceParameter[] parameters() default {};

	/**
	 * The expecteded HTTP Status codes when retrieving REST service response.
	 * 
	 * @return The expecteded HTTP Status codes.
	 */
	int[] expectedStatusCodes() default {};

}