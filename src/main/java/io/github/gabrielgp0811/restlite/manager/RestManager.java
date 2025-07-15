/**
 * 
 */
package io.github.gabrielgp0811.restlite.manager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import io.github.gabrielgp0811.restlite.converter.impl.RestServiceConverter;
import io.github.gabrielgp0811.restlite.converter.impl.RestServicesConverter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.manager.impl.RestManagerImpl;
import io.github.gabrielgp0811.restlite.service.RestService;
import io.github.gabrielgp0811.restlite.service.TypedRestService;
import io.github.gabrielgp0811.restlite.service.impl.RestServiceImpl;
import io.github.gabrielgp0811.restlite.service.impl.TypedRestServiceImpl;
import io.github.gabrielgp0811.restlite.to.RestServiceTO;
import io.github.gabrielgp0811.restlite.to.RestServicesTO;
import io.github.gabrielgp0811.restlite.util.Constants;

/**
 * The main implementation of interface {@link RestManager}.
 * 
 * @author gabrielgp0811
 */
@SuppressWarnings("rawtypes")
public abstract class RestManager {

	private static Class<? extends RestManager> restManagerImplClass = RestManagerImpl.class;

	private Map<String, Class<?>> servicesMap = new HashMap<>();

	private Map<String, RestService> cache = new HashMap<>();

	private Map<String, TypedRestService<?>> typedCache = new HashMap<>();

	private Map<String, String> defaultHeaders = null;

	protected Class<? extends RestService> restServiceImplClass = null;

	protected Class<? extends TypedRestService> restTypedRestServiceImplClass = null;

	/**
	 * @param packageNames   The name of the packages where the classes containing
	 *                       REST Services annotations are mapped.
	 * @param defaultHeaders The default headers for every service created.
	 */
	public RestManager(List<String> packageNames, Map<String, String> defaultHeaders) {
		init(packageNames, defaultHeaders);
	}

	/**
	 * @param packageNames   The name of the packages where the classes containing
	 *                       REST Services annotations are mapped.
	 * @param defaultHeaders The default headers for every service created.
	 */
	private void init(List<String> packageNames, Map<String, String> defaultHeaders) {
		if (packageNames == null) {
			packageNames = Collections.<String>emptyList();
		}

		if (defaultHeaders == null) {
			defaultHeaders = new HashMap<>();
		}

		this.defaultHeaders = defaultHeaders;

		packageNames.stream()
				.map(packageName -> packageName.replace('.', '/'))
				.map(pathname -> Thread.currentThread().getContextClassLoader().getResource(pathname))
				.map(resource -> resource.getFile())
				.map(file -> {
					try {
						return URLDecoder.decode(file, StandardCharsets.UTF_8.displayName());
					} catch (UnsupportedEncodingException e) {
						return null;
					}
				})
				.filter(Objects::nonNull)
				.forEach(this::addToMap);
	}

	/**
	 * @param pathname The pathname containing the classes to load.
	 */
	private void addToMap(String pathname) {
		if (pathname == null || pathname.trim().isEmpty()) {
			return;
		}

		do {
			try {
				if (!Files.exists(Paths.get(pathname))) {
					return;
				}

				break;
			} catch (InvalidPathException e) {
				if (pathname.isEmpty())
					return;

				pathname = pathname.substring(1);
			}
		} while (true);

		try {
			Files.list(Paths.get(pathname))
					.map(path -> path.toFile())
					.filter(file -> file.exists())
					.filter(file -> file.isFile())
					.filter(file -> file.getName().endsWith(".class"))
					.map(file -> {
						try {
							return file.toURI().toURL();
						} catch (MalformedURLException e) {
							return null;
						}
					})
					.filter(Objects::nonNull)
					.map(url -> {
						try {
							String enc = StandardCharsets.UTF_8.displayName();
							String path = URLDecoder.decode(url.getFile(), enc);
							String classLoaderPath = URLDecoder.decode(
									Thread.currentThread().getContextClassLoader().getResource("").getFile(), enc);

							String className = path.substring(classLoaderPath.length());

							className = className.replace('/', '.');
							className = className.substring(0, className.indexOf(".class"));

							return URLClassLoader.newInstance(new URL[] { url }).loadClass(className);
						} catch (ClassNotFoundException | UnsupportedEncodingException e) {
							return null;
						}
					})
					.filter(Objects::nonNull)
					.filter(clazz -> clazz
							.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestServices.class))
					.forEach(clazz -> {
						io.github.gabrielgp0811.restlite.annotation.RestServices services = clazz
								.getDeclaredAnnotation(io.github.gabrielgp0811.restlite.annotation.RestServices.class);

						Arrays.stream(services.value())
								.map(service -> service.name())
								.forEach(name -> addService(name, clazz));
					});

			Files.list(Paths.get(pathname))
					.map(path -> path.toFile())
					.filter(file -> file.exists())
					.filter(file -> file.isFile())
					.filter(file -> file.getName().endsWith(".class"))
					.map(file -> {
						try {
							return file.toURI().toURL();
						} catch (MalformedURLException e) {
							return null;
						}
					})
					.filter(Objects::nonNull)
					.map(url -> {
						try {
							String enc = StandardCharsets.UTF_8.displayName();
							String path = URLDecoder.decode(url.getFile(), enc);
							String classLoaderPath = URLDecoder.decode(
									Thread.currentThread().getContextClassLoader().getResource("").getFile(), enc);

							String className = path.substring(classLoaderPath.length());

							className = className.replace('/', '.');
							className = className.substring(0, className.indexOf(".class"));

							return URLClassLoader.newInstance(new URL[] { url }).loadClass(className);
						} catch (ClassNotFoundException | UnsupportedEncodingException e) {
							return null;
						}
					})
					.filter(Objects::nonNull)
					.filter(clazz -> clazz
							.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestService.class))
					.forEach(
							clazz -> addService(
									clazz.getDeclaredAnnotation(
											io.github.gabrielgp0811.restlite.annotation.RestService.class).name(),
									clazz));

			Files.list(Paths.get(pathname))
					.map(path -> path.toFile())
					.filter(file -> file.isDirectory())
					.map(file -> file.getAbsolutePath())
					.forEach(this::addToMap);
		} catch (IOException e) {
		}
	}

	/**
	 * @param restManagerImplClass The REST manager implementation class.
	 */
	public static void setRestManagerImplClass(Class<? extends RestManager> restManagerImplClass) {
		RestManager.restManagerImplClass = restManagerImplClass;
	}

	/**
	 * Get REST manager instance.
	 * 
	 * @return The instance or
	 *         <strong><span style="color:#7f0055">null</span></strong> if no
	 *         implementation class is informed or error occurred when instantiating
	 *         implementation class.
	 * @see #getRestManager(String, Map)
	 * @see #restManagerImplClass
	 * @see #setRestManagerImplClass(Class)
	 */
	public static RestManager getRestManager() {
		return getRestManager(System.getProperty(Constants.PACKAGE_NAME_PROPERTY), null);
	}

	/**
	 * Get REST manager instance.
	 * 
	 * @param packageNames The name of the packages where the classes containing
	 *                     REST Services annotations are mapped (It's possible to
	 *                     inform multiple packages by separating them with
	 *                     semicolon).
	 * @return The instance or
	 *         <strong><span style="color:#7f0055">null</span></strong> if no
	 *         implementation class is informed or error occurred when instantiating
	 *         implementation class.
	 * @see #getRestManager(String, Map)
	 * @see #restManagerImplClass
	 * @see #setRestManagerImplClass(Class)
	 */
	public static RestManager getRestManager(String packageNames) {
		return getRestManager(packageNames, null);
	}

	/**
	 * Get REST manager instance.
	 * 
	 * @param packageNames The name of the packages where the classes containing
	 *                     REST Services annotations are mapped.
	 * @return The instance or
	 *         <strong><span style="color:#7f0055">null</span></strong> if no
	 *         implementation class is informed or error occurred when instantiating
	 *         implementation class.
	 * @see #getRestManager(String[], Map)
	 * @see #restManagerImplClass
	 * @see #setRestManagerImplClass(Class)
	 */
	public static RestManager getRestManager(String[] packageNames) {
		return getRestManager(packageNames, null);
	}

	/**
	 * Get REST manager instance.
	 * 
	 * @param packageNames The name of the packages where the classes containing
	 *                     REST Services annotations are mapped.
	 * @return The instance or
	 *         <strong><span style="color:#7f0055">null</span></strong> if no
	 *         implementation class is informed or error occurred when instantiating
	 *         implementation class.
	 * @see #getRestManager(List, Map)
	 * @see #restManagerImplClass
	 * @see #setRestManagerImplClass(Class)
	 */
	public static RestManager getRestManager(List<String> packageNames) {
		return getRestManager(packageNames, null);
	}

	/**
	 * Get REST manager instance.
	 * 
	 * @param defaultHeaders The default headers for every service created.
	 * @return The instance or
	 *         <strong><span style="color:#7f0055">null</span></strong> if no
	 *         implementation class is informed or error occurred when instantiating
	 *         implementation class.
	 * @see #getRestManager(List, Map)
	 * @see #restManagerImplClass
	 * @see #setRestManagerImplClass(Class)
	 */
	public static RestManager getRestManager(Map<String, String> defaultHeaders) {
		return getRestManager((List<String>) null, defaultHeaders);
	}

	/**
	 * Get REST manager instance.
	 * 
	 * @param packageNames   The name of the packages where the classes containing
	 *                       REST Services annotations are mapped (It's possible to
	 *                       inform multiple packages by separating them with
	 *                       semicolon).
	 * @param defaultHeaders The default headers for every service created.
	 * @return The instance or
	 *         <strong><span style="color:#7f0055">null</span></strong> if no
	 *         implementation class is informed or error occurred when instantiating
	 *         implementation class.
	 * @see #getRestManager(String[], Map)
	 * @see #restManagerImplClass
	 * @see #setRestManagerImplClass(Class)
	 */
	public static RestManager getRestManager(String packageNames, Map<String, String> defaultHeaders) {
		return getRestManager(packageNames == null ? null : packageNames.split(";"), defaultHeaders);
	}

	/**
	 * Get REST manager instance.
	 * 
	 * @param packageNames   The name of the packages where the classes containing
	 *                       REST Services annotations are mapped.
	 * @param defaultHeaders The default headers for every service created.
	 * @return The instance or
	 *         <strong><span style="color:#7f0055">null</span></strong> if no
	 *         implementation class is informed or error occurred when instantiating
	 *         implementation class.
	 * @see #getRestManager(List, Map)
	 * @see #restManagerImplClass
	 * @see #setRestManagerImplClass(Class)
	 */
	public static RestManager getRestManager(String[] packageNames, Map<String, String> defaultHeaders) {
		return getRestManager(packageNames == null ? null : Arrays.asList(packageNames), defaultHeaders);
	}

	/**
	 * Get REST manager instance.
	 * 
	 * @param packageNames   The name of the packages where the classes containing
	 *                       REST Services annotations are mapped.
	 * @param defaultHeaders The default headers for every service created.
	 * @return The instance or
	 *         <strong><span style="color:#7f0055">null</span></strong> if no
	 *         implementation class is informed or error occurred when instantiating
	 *         implementation class.
	 * @see #restManagerImplClass
	 * @see #setRestManagerImplClass(Class)
	 */
	public static RestManager getRestManager(List<String> packageNames, Map<String, String> defaultHeaders) {
		Class<? extends RestManager> restManagerImplClass = RestManager.restManagerImplClass;

		if (restManagerImplClass == null) {
			return null;
		}

		try {
			Constructor<? extends RestManager> constructor = restManagerImplClass.getConstructor(List.class, Map.class);

			return constructor.newInstance(packageNames, defaultHeaders);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
		}

		return null;
	}

	/**
	 * @param restServiceImplClass The implementation of interface
	 *                             {@link io.github.gabrielgp0811.restlite.service.RestService}.
	 */
	public void setRestServiceImplClass(Class<? extends RestService> restServiceImplClass) {
		this.restServiceImplClass = restServiceImplClass;
	}

	/**
	 * @param restTypedRestServiceImplClass The implementation of interface
	 *                                      {@link io.github.gabrielgp0811.restlite.service.TypedRestService}.
	 */
	public void setTypedRestServiceImplClass(Class<? extends TypedRestService> restTypedRestServiceImplClass) {
		this.restTypedRestServiceImplClass = restTypedRestServiceImplClass;
	}

	/**
	 * @param name  The name of REST Service.
	 * @param clazz The class containing the REST service information.
	 */
	public void addService(String name, Class<?> clazz) {
		if (name == null || name.trim().isEmpty() || clazz == null || servicesMap.containsKey(name)) {
			return;
		}

		if (clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestServices.class)
				&& !Arrays.stream(clazz.getDeclaredAnnotation(io.github.gabrielgp0811.restlite.annotation.RestServices.class).value())
						.filter(service -> service.name().equals(name))
						.findAny()
						.isPresent()
				|| (clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestService.class)
						&& !clazz.getDeclaredAnnotation(io.github.gabrielgp0811.restlite.annotation.RestService.class)
								.name().equals(name))) {
			return;
		}

		servicesMap.put(name, clazz);
	}

	/**
	 * @param names The names of REST Services.
	 * @param clazz The class containing the REST service information.
	 */
	public void addServices(String[] names, Class<?> clazz) {
		if (names == null || names.length == 0 || clazz == null) {
			return;
		}

		Arrays.stream(names).filter(Objects::nonNull).forEach(name -> addService(name, clazz));
	}

	/**
	 * Creates an empty {@link RestService}.
	 * 
	 * @return The empty {@link RestService}.
	 * @throws RestException Error creating {@link RestService}.
	 */
	public RestService createService() throws RestException {
		RestService service = null;

		Class<? extends RestService> restServiceImplClass = this.restServiceImplClass;

		if (restServiceImplClass == null) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR,
					"No implementation class provided.");
		}

		try {
			service = restServiceImplClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR,
					"Error instantiating implementation class '" + restServiceImplClass.getName() + "'.", e);
		}

		service.setHeaders(defaultHeaders);

		return service;
	}

	/**
	 * Creates a {@link RestService} from cache.
	 * 
	 * @param name The name of REST Service.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 */
	public RestService createService(String name) throws RestException {
		if (name == null || name.trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "Must inform a REST Service name.");
		}

		if (cache.containsKey(name)) {
			return cache.get(name);
		}

		if (!servicesMap.containsKey(name)) {
			throw new RestException(HttpURLConnection.HTTP_NOT_FOUND, "REST Service '" + name + "' not found.");
		}

		return createService(name, servicesMap.get(name));
	}

	/**
	 * Creates a {@link RestService} with specified <code>name</code> contained on
	 * <code>clazz</code>.
	 * 
	 * @param name  The name of REST Service.
	 * @param clazz The class containing the REST service with specified
	 *              <code>name</code>.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 */
	public RestService createService(String name, Class<?> clazz) throws RestException {
		if (name == null || name.trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "Must inform a REST Service name.");
		}

		if (cache.containsKey(name)) {
			return cache.get(name);
		}

		if (clazz == null) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Must inform a class which contains the REST Service '" + name + "'.");
		}

		if (!clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestServices.class)
				&& !clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestService.class)) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Class '" + clazz.getName() + "' doesn't have required RestServices or RestService annotations.");
		}

		RestServiceTO to = null;

		if (clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestServices.class)) {
			RestServicesTO rest = new RestServicesConverter().convert(
					clazz.getDeclaredAnnotation(io.github.gabrielgp0811.restlite.annotation.RestServices.class));

			to = rest.getServices().stream().filter(service -> service.getName().equals(name)).findFirst()
					.orElseThrow(() -> new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
							"Class '" + clazz.getName() + "' doesn't have REST Service '" + name + "'."));
		} else if (clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestService.class)) {
			to = new RestServiceConverter().convert(
					clazz.getDeclaredAnnotation(io.github.gabrielgp0811.restlite.annotation.RestService.class));

			if (!to.getName().equals(name)) {
				throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
						"Class '" + clazz.getName() + "' doesn't have REST Service '" + name + "'.");
			}
		}

		String protocol = null;
		String host = null;
		int port = -1;
		String file = null;
		try {
			URL url = new URL(to.getUrl().trim());

			protocol = url.getProtocol();
			host = url.getHost();
			port = url.getPort();
			file = url.getFile();
		} catch (MalformedURLException e) {
			protocol = to.getProtocol().trim();
			host = to.getHost().trim();
			port = to.getPort();

			if (!to.getApp().trim().isEmpty()) {
				file = to.getApp().trim();
			}

			if (!to.getPath().trim().isEmpty()) {
				if (file == null) {
					file = to.getPath().trim();
				} else {
					if (!file.startsWith("/")) {
						file = "/".concat(file);
					}

					file = file.concat("/").concat(to.getPath().trim());
				}
			}
		}

		RestService service = new RestServiceImpl();

		service.setProtocol(protocol);
		service.setHost(host);
		service.setPort(port);
		service.setFile(file);
		service.setMethod(to.getMethod());
		service.setContentType(to.getContentType());
		service.setConnectTimeout(to.getConnectTimeout());
		service.setReadTimeout(to.getReadTimeout());
		service.setHeaders(defaultHeaders);
		service.setReadCharset(to.getCharsetRead());
		service.setWriteCharset(to.getCharsetWrite());
		service.setExpectedStatusCodes(to.getExpectedStatusCodes());

		Arrays.asList(to.getParameters())
				.forEach(parameter -> service.setParameterInfo(parameter.getName(), parameter.getType(),
						parameter.isOptional(), parameter.getDateTimeFormat().getPattern(), parameter.getDateTimeFormat().getLocale(),
						parameter.getDateTimeFormat().getTimezone()));

		Arrays.asList(to.getHeaders()).forEach(property -> service.setHeader(property.getName(), property.getValue()));

		cache.put(name, service);

		return service;
	}

	/**
	 * Creates an empty {@link TypedRestService}.
	 * 
	 * @param <T>         Generic type for the result class.
	 * @param resultClass The result class.
	 * @return The empty {@link TypedRestService}.
	 * @throws RestException Error creating {@link TypedRestService}.
	 */
	@SuppressWarnings("unchecked")
	public <T> TypedRestService<T> createTypedService(Class<T> resultClass) throws RestException {
		TypedRestService<T> service = null;

		Class<? extends TypedRestService> restTypedServiceImplClass = this.restTypedRestServiceImplClass;

		if (restTypedServiceImplClass == null) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR,
					"No implementation class provided.");
		}

		try {
			service = restTypedServiceImplClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR,
					"Error instantiating implementation class '" + restTypedServiceImplClass.getName() + "'.", e);
		}

		service.setHeaders(defaultHeaders);

		return service;
	}

	/**
	 * Creates a {@link TypedRestService} from cache.
	 * 
	 * @param <T>         Generic type for the result class.
	 * @param name        The name of REST Service.
	 * @param resultClass The result class.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 */
	@SuppressWarnings("unchecked")
	public <T> TypedRestService<T> createTypedService(String name, Class<T> resultClass) throws RestException {
		if (name == null || name.trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "Must inform a REST Service name.");
		}

		if (typedCache.containsKey(name)) {
			return (TypedRestService<T>) typedCache.get(name);
		}

		if (!servicesMap.containsKey(name)) {
			throw new RestException(HttpURLConnection.HTTP_NOT_FOUND,
					"Must inform a class which contains the REST Service '" + name + "'.");
		}

		return createTypedService(name, servicesMap.get(name), resultClass);
	}

	/**
	 * Creates a {@link TypedRestService} with specified <code>name</code>
	 * contained on <code>clazz</code>.
	 * 
	 * @param <T>         Generic type for the result class.
	 * @param name        The name of REST Service.
	 * @param clazz       The class containing the REST Service with specified
	 *                    <code>name</code>.
	 * @param resultClass The result class.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 */
	@SuppressWarnings("unchecked")
	public <T> TypedRestService<T> createTypedService(String name, Class<?> clazz, Class<T> resultClass)
			throws RestException {
		if (name == null || name.trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "Must inform a REST Service name.");
		}

		if (typedCache.containsKey(name)) {
			return (TypedRestService<T>) typedCache.get(name);
		}

		if (clazz == null) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Must inform a class which contains the REST Service '" + name + "'.");
		}

		if (resultClass == null) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "Must inform a result class.");
		}

		if (!clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestServices.class)
				&& !clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestService.class)) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Class '" + clazz.getName() + "' doesn't have required RestServices or RestService annotations.");
		}

		RestServiceTO to = null;

		if (clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestServices.class)) {
			RestServicesTO rest = new RestServicesConverter().convert(
					clazz.getDeclaredAnnotation(io.github.gabrielgp0811.restlite.annotation.RestServices.class));

			to = rest.getServices().stream().filter(service -> service.getName().equals(name)).findFirst()
					.orElseThrow(() -> new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
							"Class '" + clazz.getName() + "' doesn't have REST Service '" + name + "'."));
		} else if (clazz.isAnnotationPresent(io.github.gabrielgp0811.restlite.annotation.RestService.class)) {
			to = new RestServiceConverter().convert(
					clazz.getDeclaredAnnotation(io.github.gabrielgp0811.restlite.annotation.RestService.class));

			if (!to.getName().equals(name)) {
				throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
						"Class '" + clazz.getName() + "' doesn't have REST Service '" + name + "'.");
			}
		}

		String url = to.getUrl().trim();
		if (url.isEmpty()) {
			url = to.getProtocol().trim().concat("://").concat(to.getHost().trim());

			if (to.getPort() > 0) {
				url = url.concat(":").concat(Integer.toString(to.getPort()));
			}

			if (!to.getApp().trim().isEmpty()) {
				url = url.concat("/").concat(to.getApp());
			}

			if (!to.getPath().trim().isEmpty()) {
				url = url.concat("/").concat(to.getPath());
			}
		}

		TypedRestService<T> service = new TypedRestServiceImpl<>();

		service.setUrl(url);
		service.setMethod(to.getMethod());
		service.setContentType(to.getContentType());
		service.setConnectTimeout(to.getConnectTimeout());
		service.setReadTimeout(to.getReadTimeout());
		service.setHeaders(defaultHeaders);
		service.setReadCharset(to.getCharsetRead());
		service.setWriteCharset(to.getCharsetWrite());
		service.setExpectedStatusCodes(to.getExpectedStatusCodes());
		service.setResultClass(resultClass);

		Arrays.asList(to.getParameters())
				.forEach(parameter -> service.setParameterInfo(parameter.getName(), parameter.getType(),
						parameter.isOptional(), parameter.getDateTimeFormat().getPattern(), parameter.getDateTimeFormat().getLocale(),
						parameter.getDateTimeFormat().getTimezone()));

		Arrays.asList(to.getHeaders()).forEach(property -> service.setHeader(property.getName(), property.getValue()));

		typedCache.put(name, service);

		return service;
	}

	/**
	 * Creates a {@link RestService} from Context environment with specified JNDI
	 * name <code>jndiName</code>.
	 * 
	 * @param jndiName The name of the object to look up.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 */
	public RestService createServiceFromJNDI(String jndiName) throws RestException {
		return createServiceFromJNDI(jndiName, null);
	}

	/**
	 * Creates a {@link RestService} from Context environment with specified JNDI
	 * name <code>jndiName</code>.
	 * 
	 * @param jndiName The name of the object to look up.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 */
	public RestService createServiceFromJNDI(String jndiName, Hashtable<?, ?> environment) throws RestException {
		if (jndiName == null || jndiName.trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "Must inform JNDI context.");
		}

		Context context = null;

		try {
			context = new InitialContext(environment);
		} catch (NamingException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR, "Error obtaining context.", e);
		}

		RestService result = null;

		try {
			result = (RestService) context.lookup(jndiName);
		} catch (NamingException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR,
					"Error obtaining JNDI object '" + jndiName + "'.", e);
		} catch (ClassCastException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR,
					"JNDI object '" + jndiName + "' doesn't implement IRestService interface.", e);
		}

		return result;
	}

	/**
	 * Creates a {@link TypedRestService} from Context environment with specified
	 * JNDI name <code>jndiName</code>.
	 * 
	 * @param <T>         Generic type for the result class.
	 * @param jndiName    The name of the object to look up.
	 * @param resultClass The result class.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 */
	public <T> TypedRestService<T> createTypedServiceFromJNDI(String jndiName, Class<T> resultClass)
			throws RestException {
		return createTypedServiceFromJNDI(jndiName, null, resultClass);
	}

	/**
	 * Creates a {@link TypedRestService} from context environment using specified
	 * JNDI name <code>jndiName</code> to look up for the object.
	 * 
	 * @param <T>         Generic type for the result class.
	 * @param jndiName    The name of the object to look up.
	 * @param environment Used to create the initial context
	 *                    (<strong><span style="color:#7f0055">null</span></strong>
	 *                    indicates an empty environment).
	 * @param resultClass The result class.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 */
	@SuppressWarnings("unchecked")
	public <T> TypedRestService<T> createTypedServiceFromJNDI(String jndiName, Hashtable<?, ?> environment,
			Class<T> resultClass) throws RestException {
		if (jndiName == null || jndiName.trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST, "Must inform JNDI context.");
		}

		Context context = null;

		try {
			context = new InitialContext(environment);
		} catch (NamingException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR, "Error obtaining context.", e);
		}

		TypedRestService<T> result = null;

		try {
			result = (TypedRestService<T>) context.lookup(jndiName);
		} catch (NamingException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR,
					"Error obtaining JNDI object '" + jndiName + "'.", e);
		} catch (ClassCastException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR,
					"JNDI object '" + jndiName + "' doesn't implement ITypedRestService interface.", e);
		}

		return result;
	}

}