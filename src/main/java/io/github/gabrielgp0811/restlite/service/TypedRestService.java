/**
 * 
 */
package io.github.gabrielgp0811.restlite.service;

import java.util.List;
import java.util.stream.Stream;

import io.github.gabrielgp0811.restlite.exception.RestException;

/**
 * Main interface for the Typed REST Service.
 * 
 * @param <T> Generic type for the result class.
 * @author gabrielgp0811
 */
public interface TypedRestService<T> extends RestService {

	/**
	 * @param resultClass The result class.
	 */
	void setResultClass(Class<T> resultClass);

	/**
	 * @return A single object from service call.
	 * @throws RestException Error on service call.
	 */
	T getSingleResult() throws RestException;

	/**
	 * @return The <code>java.util.List</code> containing the objects from service
	 *         call.
	 * @throws RestException Error on service call.
	 */
	List<T> getResultList() throws RestException;

	/**
	 * Same as <code>getResultList().stream()</code>.
	 * 
	 * @return The sequential stream.
	 * @throws RestException Error obtaining the sequential stream.
	 * @see #getResultList()
	 */
	default Stream<T> getResultStream() throws RestException {
		return getResultList().stream();
	}

}