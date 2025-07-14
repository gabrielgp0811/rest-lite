/**
 * 
 */
package io.github.gabrielgp0811.restlite.consumer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.manager.RestManager;
import io.github.gabrielgp0811.restlite.service.RestService;
import io.github.gabrielgp0811.restlite.service.TypedRestService;

/**
 * @param <T> Generic type.
 * @author gabrielgp0811
 */
public abstract class ServiceConsumer<T> {

	/**
	 * The attribute representing the class generic type <code>&lt;T&gt;</code>.
	 */
	private Class<T> type = null;

	protected RestManager manager = null;

	/**
	 * 
	 */
	public ServiceConsumer() {
		manager = RestManager.getRestManager();
		type = getType();
	}

	/**
	 * @param packageName The package name of the classes containing REST services.
	 */
	public ServiceConsumer(String packageName) {
		manager = RestManager.getRestManager(packageName);
		type = getType();
	}

	/**
	 * @param packageNames The name of the packages where the classes containing
	 *                     REST Services annotations are mapped.
	 */
	public ServiceConsumer(String[] packageNames) {
		manager = RestManager.getRestManager(packageNames);
		type = getType();
	}

	/**
	 * @param packageNames The name of the packages where the classes containing
	 *                     REST Services annotations are mapped.
	 */
	public ServiceConsumer(List<String> packageNames) {
		manager = RestManager.getRestManager(packageNames);
		type = getType();
	}

	/**
	 * @param defaultHeaders The default headers for every service created.
	 */
	public ServiceConsumer(Map<String, String> defaultHeaders) {
		manager = RestManager.getRestManager(defaultHeaders);
		type = getType();
	}

	/**
	 * @param packageName    The package name of the classes containing REST
	 *                       services.
	 * @param defaultHeaders The default headers for every service created.
	 */
	public ServiceConsumer(String packageName, Map<String, String> defaultHeaders) {
		manager = RestManager.getRestManager(packageName, defaultHeaders);
		type = getType();
	}

	/**
	 * @param packageNames   The name of the packages where the classes containing
	 *                       REST Services annotations are mapped.
	 * @param defaultHeaders The default headers for every service created.
	 */
	public ServiceConsumer(String[] packageNames, Map<String, String> defaultHeaders) {
		manager = RestManager.getRestManager(packageNames, defaultHeaders);
		type = getType();
	}

	/**
	 * @param packageNames   The name of the packages where the classes containing
	 *                       REST Services annotations are mapped.
	 * @param defaultHeaders The default headers for every service created.
	 */
	public ServiceConsumer(List<String> packageNames, Map<String, String> defaultHeaders) {
		manager = RestManager.getRestManager(packageNames, defaultHeaders);
		type = getType();
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getSingleResult(name, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name The name of REST service.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getSingleResult(String, Class)
	 */
	protected Object getSingleResult(String name) throws RestException {
		return getSingleResult(name, type);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * 
	 * @param name       The name of REST service.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 */
	protected Object getSingleResult(String name, String paramName, Object paramValue) throws RestException {
		return getSingleResult(name, type, paramName, paramValue);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * 
	 * @param name        The name of REST service.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 */
	protected Object getSingleResult(String name, String[] paramNames, Object[] paramValues) throws RestException {
		return getSingleResult(name, type, paramNames, paramValues);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getSingleResult(name, clazz, <strong><span style=
	 * "color:#7f0055">new</span></strong> String[0], <strong><span style=
	 * "color:#7f0055">new</span></strong> Object[0])</code>.
	 * </p>
	 * 
	 * @param name  The name of REST service.
	 * @param clazz The class containing the REST service with specified
	 *              <code>name</code>.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getSingleResult(String, Class, String[], Object[])
	 */
	protected Object getSingleResult(String name, Class<?> clazz) throws RestException {
		return getSingleResult(name, clazz, new String[0], new Object[0]);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getSingleResult(name, clazz, <strong><span style=
	 * "color:#7f0055">new</span></strong> String[] { paramName }, <strong><span style=
	 * "color:#7f0055">new</span></strong> Object[] { paramValue })</code>.
	 * </p>
	 * 
	 * @param name       The name of REST service.
	 * @param clazz      The class containing the REST service with specified
	 *                   <code>name</code>.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getSingleResult(String, Class, String[], Object[])
	 */
	protected Object getSingleResult(String name, Class<?> clazz, String paramName, Object paramValue)
			throws RestException {
		return getSingleResult(name, clazz, new String[] { paramName }, new Object[] { paramValue });
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * 
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 */
	protected Object getSingleResult(String name, Class<?> clazz, String[] paramNames, Object[] paramValues)
			throws RestException {
		RestService service = createService(name, clazz, paramNames, paramValues);

		return service.getSingleResult();
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getResultList(name, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name The name of REST service.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getResultList(String, Class)
	 */
	protected List<?> getResultList(String name) throws RestException {
		return getResultList(name, type);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as
	 * <code>getResultList(name, {@link #type}, paramName, paramValue)</code>.
	 * </p>
	 * 
	 * @param name       The name of REST service.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getResultList(String, Class, String, Object)
	 */
	protected List<?> getResultList(String name, String paramName, Object paramValue) throws RestException {
		return getResultList(name, type, paramName, paramValue);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getResultList(name, {@link #type}, <strong><span style=
	 * "color:#7f0055">new</span></strong> String[] { paramName }, <strong><span style=
	 * "color:#7f0055">new</span></strong> Object[] { paramValue })</code>.
	 * </p>
	 * 
	 * @param name        The name of REST service.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getResultList(String, Class, String[], Object[])
	 */
	protected List<?> getResultList(String name, String[] paramNames, Object[] paramValues) throws RestException {
		return getResultList(name, type, paramNames, paramValues);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getResultList(name, clazz, <strong><span style=
	 * "color:#7f0055">new</span></strong> String[0], <strong><span style=
	 * "color:#7f0055">new</span></strong> Object[0])</code>.
	 * </p>
	 * 
	 * @param name  The name of REST service.
	 * @param clazz The class containing the REST service with specified
	 *              <code>name</code>.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getResultList(String, Class, String[], Object[])
	 */
	protected List<?> getResultList(String name, Class<?> clazz) throws RestException {
		return getResultList(name, clazz, new String[0], new Object[0]);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getResultList(name, clazz, <strong><span style=
	 * "color:#7f0055">new</span></strong> String[] { paramName }, <strong><span style=
	 * "color:#7f0055">new</span></strong> Object[] { paramValue })</code>.
	 * </p>
	 * 
	 * @param name       The name of REST service.
	 * @param clazz      The class containing the REST service with specified
	 *                   <code>name</code>.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getResultList(String, Class, String[], Object[])
	 */
	protected List<?> getResultList(String name, Class<?> clazz, String paramName, Object paramValue)
			throws RestException {
		return getResultList(name, clazz, new String[] { paramName }, new Object[] { paramValue });
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * 
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 */
	protected List<?> getResultList(String name, Class<?> clazz, String[] paramNames, Object[] paramValues)
			throws RestException {
		RestService service = createService(name, clazz, paramNames, paramValues);

		return service.getResultList();
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getTypedSingleResult(name, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name The name of REST service.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getTypedSingleResult(String, Class)
	 */
	protected T getTypedSingleResult(String name) throws RestException {
		return getTypedSingleResult(name, type);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as
	 * <code>getTypedSingleResult(name, {@link #type}, paramName, paramValue, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name       The name of REST service.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getTypedSingleResult(String, Class, String, Object, Class)
	 */
	protected T getTypedSingleResult(String name, String paramName, Object paramValue) throws RestException {
		return getTypedSingleResult(name, type, paramName, paramValue, type);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as
	 * <code>gettypedsingleResult(name, {@link #type}, paramnames, paramValues, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name        The name of REST service.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getTypedSingleResult(String, Class, String[], Object[], Class)
	 */
	protected T getTypedSingleResult(String name, String[] paramNames, Object[] paramValues) throws RestException {
		return getTypedSingleResult(name, type, paramNames, paramValues, type);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getTypedSingleResult(name, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name  The name of REST service.
	 * @param clazz The class containing the REST service with specified
	 *              <code>name</code>.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getTypedSingleResult(String, Class, Class)
	 */
	protected T getTypedSingleResult(String name, Class<?> clazz) throws RestException {
		return getTypedSingleResult(name, clazz, type);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as
	 * <code>getTypedSingleResult(name, paramName, paramValue, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name       The name of REST service.
	 * @param clazz      The class containing the REST service with specified
	 *                   <code>name</code>.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getTypedSingleResult(String, Class, String, Object, Class)
	 */
	protected T getTypedSingleResult(String name, Class<?> clazz, String paramName, Object paramValue)
			throws RestException {
		return getTypedSingleResult(name, clazz, paramName, paramValue, type);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as
	 * <code>gettypedsingleResult(name, clazz, paramnames, paramValues, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getTypedSingleResult(String, Class, String[], Object[], Class)
	 */
	protected T getTypedSingleResult(String name, Class<?> clazz, String[] paramNames, Object[] paramValues)
			throws RestException {
		return getTypedSingleResult(name, clazz, paramNames, paramValues, type);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getTypedSingleResult(name, clazz, <strong><span style=
	 * "color:#7f0055">new</span></strong> String[0], <strong><span style=
	 * "color:#7f0055">new</span></strong> Object[0], resultClass)</code>.
	 * </p>
	 * 
	 * @param <K>         Generic type for the result class.
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param resultClass The result class.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getTypedSingleResult(String, Class, String[], Object[], Class)
	 */
	protected <K> K getTypedSingleResult(String name, Class<?> clazz, Class<K> resultClass) throws RestException {
		return getTypedSingleResult(name, clazz, new String[0], new Object[0], resultClass);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getTypedSingleResult(name, clazz, <strong><span style=
	 * "color:#7f0055">new</span></strong> String[] { paramName }, <strong><span style=
	 * "color:#7f0055">new</span></strong> Object[] { paramValue }, resultClass)</code>.
	 * </p>
	 * 
	 * @param <K>         Generic type for the result class.
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramName   The parameter names.
	 * @param paramValue  The parameter values.
	 * @param resultClass The result class.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 * @see #getTypedSingleResult(String, Class, String[], Object[], Class)
	 */
	protected <K> K getTypedSingleResult(String name, Class<?> clazz, String paramName, Object paramValue,
			Class<K> resultClass) throws RestException {
		return getTypedSingleResult(name, clazz, new String[] { paramName }, new Object[] { paramValue }, resultClass);
	}

	/**
	 * Obtains a single object from service call with specified <code>name</code>.
	 * 
	 * @param <K>         Generic type for the result class.
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @param resultClass The result class.
	 * @return The result object.
	 * @throws RestException Error on service call.
	 */
	protected <K> K getTypedSingleResult(String name, Class<?> clazz, String[] paramNames, Object[] paramValues,
			Class<K> resultClass) throws RestException {
		TypedRestService<K> service = createTypedService(name, clazz, paramNames, paramValues, resultClass);

		return service.getSingleResult();
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getTypedResultList(name, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name The name of REST service.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getTypedResultList(String, Class)
	 */
	protected List<T> getTypedResultList(String name) throws RestException {
		return getTypedResultList(name, type);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getTypedResultList(name, clazz, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name  The name of REST service.
	 * @param clazz The class containing the REST service with specified
	 *              <code>name</code>.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getTypedResultList(String, Class, Class)
	 */
	protected List<T> getTypedResultList(String name, Class<?> clazz) throws RestException {
		return getTypedResultList(name, clazz, type);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getTypedResultList(name, clazz, <strong><span style=
	 * "color:#7f0055">new</span></strong> String[0], <strong><span style=
	 * "color:#7f0055">new</span></strong> Object[0], resultClass)</code>.
	 * </p>
	 * 
	 * @param <K>         Generic type for the result class.
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param resultClass The result class.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getTypedResultList(String, Class, String[], Object[], Class)
	 */
	protected <K> List<K> getTypedResultList(String name, Class<?> clazz, Class<K> resultClass) throws RestException {
		return getTypedResultList(name, clazz, new String[0], new Object[0], resultClass);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as
	 * <code>getTypedResultList(name, clazz, paramName, paramValue, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name       The name of REST service.
	 * @param clazz      The class containing the REST service with specified
	 *                   <code>name</code>.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getTypedResultList(String, Class, String, Object, Class)
	 */
	protected List<T> getTypedResultList(String name, Class<?> clazz, String paramName, Object paramValue)
			throws RestException {
		return getTypedResultList(name, clazz, paramName, paramValue, type);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as <code>getTypedResultList(name, clazz, <strong><span style=
	 * "color:#7f0055">new</span></strong> String[] { paramName }, <strong><span style=
	 * "color:#7f0055">new</span></strong> Object[] { paramValue }, resultClass)</code>.
	 * </p>
	 * 
	 * @param <K>         Generic type for the result class.
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramName   The parameter name.
	 * @param paramValue  The parameter value.
	 * @param resultClass The result class.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getTypedResultList(String, Class, String[], Object[], Class)
	 */
	protected <K> List<K> getTypedResultList(String name, Class<?> clazz, String paramName, Object paramValue,
			Class<K> resultClass) throws RestException {
		return getTypedResultList(name, clazz, new String[] { paramName }, new Object[] { paramValue }, resultClass);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * <p>
	 * Same as
	 * <code>getTypedResultList(name, clazz, paramNames, paramValues, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 * @see #getTypedResultList(String, Class, String[], Object[], Class)
	 */
	protected List<T> getTypedResultList(String name, Class<?> clazz, String[] paramNames, Object[] paramValues)
			throws RestException {
		return getTypedResultList(name, clazz, paramNames, paramValues, type);
	}

	/**
	 * Obtains a <code>java.util.List</code> containing the objects from service
	 * call with specified <code>name</code>.
	 * 
	 * @param <K>         Generic type for the result class.
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @param resultClass The result class.
	 * @return The <code>java.util.List</code> containing the result objects.
	 * @throws RestException Error on service call.
	 */
	protected <K> List<K> getTypedResultList(String name, Class<?> clazz, String[] paramNames, Object[] paramValues,
			Class<K> resultClass) throws RestException {
		TypedRestService<K> service = createTypedService(name, clazz, paramNames, paramValues, resultClass);

		return service.getResultList();
	}

	/**
	 * Creates a {@link RestService} with specified <code>name</code> contained on
	 * {@link #type}.
	 * <p>
	 * Same as <code>createService(name, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name The name of REST service.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 * @see #type
	 * @see #createService(String, Class)
	 */
	public RestService createService(String name) throws RestException {
		return createService(name, type);
	}

	/**
	 * Creates a {@link RestService} with specified <code>name</code> contained on
	 * {@link #type}.
	 * 
	 * @param name       The name of REST service.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 * @see #type
	 * @see #createService(String, String[], Object[])
	 */
	public RestService createService(String name, String paramName, Object paramValue) throws RestException {
		return createService(name, new String[] { paramName }, new Object[] { paramValue });
	}

	/**
	 * Creates a {@link RestService} with specified <code>name</code> contained on
	 * {@link #type}.
	 * 
	 * @param name        The name of REST service.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 * @see #type
	 * @see #createService(String, Class, String[], Object[])
	 */
	public RestService createService(String name, String[] paramNames, Object[] paramValues) throws RestException {
		return createService(name, type, paramNames, paramValues);
	}

	/**
	 * Creates a {@link RestService} with specified <code>name</code> contained on
	 * <code>clazz</code>.
	 * 
	 * @param name  The name of REST service.
	 * @param clazz The class containing the REST service with specified
	 *              <code>name</code>.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 */
	public RestService createService(String name, Class<?> clazz) throws RestException {
		return manager.createService(name, clazz);
	}

	/**
	 * Creates a {@link RestService} with specified <code>name</code> contained on
	 * <code>clazz</code>.
	 * 
	 * @param name       The name of REST service.
	 * @param clazz      The class containing the REST service with specified
	 *                   <code>name</code>.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 */
	public RestService createService(String name, Class<?> clazz, String paramName, Object paramValue)
			throws RestException {
		return createService(name, clazz, new String[] { paramName }, new Object[] { paramValue });
	}

	/**
	 * Creates a {@link RestService} with specified <code>name</code> contained on
	 * <code>clazz</code>.
	 * 
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @return The {@link RestService} object.
	 * @throws RestException Error creating {@link RestService} object.
	 */
	public RestService createService(String name, Class<?> clazz, String[] paramNames, Object[] paramValues)
			throws RestException {
		RestService service = createService(name, clazz);

		if (paramNames == null) {
			paramNames = new String[0];
		}

		if (paramValues == null) {
			paramValues = new Object[0];
		}

		if (paramNames.length != paramValues.length) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Parameter names and values length don't match.");
		}

		for (int i = 0; i < paramNames.length; i++) {
			String paramName = paramNames[i];
			Object paramValue = paramValues[i];

			service.setParameter(paramName, paramValue);
		}

		return service;
	}

	/**
	 * Creates a {@link TypedRestService} with specified <code>name</code> contained
	 * on {@link #type}.
	 * <p>
	 * Same as <code>createTypedService(name, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name The name of REST service.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 * @see #type
	 * @see #createTypedService(String, Class)
	 */
	public TypedRestService<T> createTypedService(String name) throws RestException {
		return createTypedService(name, type);
	}

	/**
	 * Creates a {@link TypedRestService} with specified <code>name</code> contained
	 * on <code>clazz</code>.
	 * <p>
	 * Same as <code>createTypedService(name, clazz, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name  The name of REST service.
	 * @param clazz The class containing the REST service with specified
	 *              <code>name</code>.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 * @see #type
	 * @see #createTypedService(String, Class, Class)
	 */
	public TypedRestService<T> createTypedService(String name, Class<?> clazz) throws RestException {
		return createTypedService(name, clazz, type);
	}

	/**
	 * Creates a {@link TypedRestService} with specified <code>name</code> contained
	 * on <code>clazz</code>.
	 * 
	 * @param <K>         Generic type for result class.
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param resultClass The result class.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 */
	public <K> TypedRestService<K> createTypedService(String name, Class<?> clazz, Class<K> resultClass)
			throws RestException {
		return manager.createTypedService(name, clazz, resultClass);
	}

	/**
	 * Creates a {@link TypedRestService} with specified <code>name</code> contained
	 * on {@link #type}.
	 * <p>
	 * Same as
	 * <code>createTypedService(name, new String[] { paramName }, new Object[] { paramValue }, resultClass)</code>.
	 * </p>
	 * 
	 * @param <K>         Generic type for result class.
	 * @param name        The name of REST service.
	 * @param paramName   The parameter name.
	 * @param paramValue  The parameter value.
	 * @param resultClass The result class.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 * @see #type
	 * @see #createTypedService(String, String[], Object[], Class)
	 */
	public <K> TypedRestService<K> createTypedService(String name, String paramName, Object paramValue,
			Class<K> resultClass) throws RestException {
		return createTypedService(name, new String[] { paramName }, new Object[] { paramValue }, resultClass);
	}

	/**
	 * Creates a {@link TypedRestService} with specified <code>name</code> contained
	 * on {@link #type}.
	 * <p>
	 * Same as
	 * <code>createTypedService(name, {@link #type}, paramNames, paramValues, resultClass)</code>.
	 * </p>
	 * 
	 * @param <K>         Generic type for result class.
	 * @param name        The name of REST service.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @param resultClass The result class.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 * @see #type
	 * @see #createTypedService(String, Class, String[], Object[], Class)
	 */
	public <K> TypedRestService<K> createTypedService(String name, String[] paramNames, Object[] paramValues,
			Class<K> resultClass) throws RestException {
		return createTypedService(name, type, paramNames, paramValues, resultClass);
	}

	/**
	 * Creates a {@link TypedRestService} with specified <code>name</code> contained
	 * on <code>clazz</code>.
	 * <p>
	 * Same as
	 * <code>createTypedService(name, clazz, paramName, paramValue, {@link #type})</code>.
	 * </p>
	 * 
	 * @param name       The name of REST service.
	 * @param clazz      The class containing the REST service with specified
	 *                   <code>name</code>.
	 * @param paramName  The parameter name.
	 * @param paramValue The parameter value.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 * @see #type
	 * @see #createTypedService(String, Class, String, Object, Class)
	 */
	public TypedRestService<T> createTypedService(String name, Class<?> clazz, String paramName, Object paramValue)
			throws RestException {
		return createTypedService(name, clazz, paramName, paramValue, type);
	}

	/**
	 * Creates a {@link TypedRestService} with specified <code>name</code> contained
	 * on <code>clazz</code>.
	 * <p>
	 * Same as
	 * <code>createTypedService(name, clazz, new String[] { paramName }, new Object[] { paramValue }, resultClass)</code>.
	 * </p>
	 * 
	 * @param <K>         Generic type for result class.
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramName   The parameter name.
	 * @param paramValue  The parameter value.
	 * @param resultClass The result class.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 * @see #createTypedService(String, Class, String[], Object[], Class)
	 */
	public <K> TypedRestService<K> createTypedService(String name, Class<?> clazz, String paramName, Object paramValue,
			Class<K> resultClass) throws RestException {
		return createTypedService(name, clazz, new String[] { paramName }, new Object[] { paramValue }, resultClass);
	}

	/**
	 * Creates a {@link TypedRestService} with specified <code>name</code> contained
	 * on <code>clazz</code>.
	 * 
	 * @param <K>         Generic type for result class.
	 * @param name        The name of REST service.
	 * @param clazz       The class containing the REST service with specified
	 *                    <code>name</code>.
	 * @param paramNames  The parameter names.
	 * @param paramValues The parameter values.
	 * @param resultClass The result class.
	 * @return The {@link TypedRestService} object.
	 * @throws RestException Error creating {@link TypedRestService} object.
	 */
	public <K> TypedRestService<K> createTypedService(String name, Class<?> clazz, String[] paramNames,
			Object[] paramValues, Class<K> resultClass) throws RestException {
		TypedRestService<K> service = createTypedService(name, clazz, resultClass);

		if (paramNames == null) {
			paramNames = new String[0];
		}

		if (paramValues == null) {
			paramValues = new Object[0];
		}

		if (paramNames.length != paramValues.length) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Parameter names and values length don't match.");
		}

		for (int i = 0; i < paramNames.length; i++) {
			String paramName = paramNames[i];
			Object paramValue = paramValues[i];

			service.setParameter(paramName, paramValue);
		}

		return service;
	}

	/**
	 * @return The generic type.
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getType() {
		try {
			ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();

			Type[] types = parameterizedType.getActualTypeArguments();

			return (Class<T>) types[0];
		} catch (ClassCastException e) {
		}

		return null;
	}

}