/**
 * 
 */
package io.github.gabrielgp0811.restlite.service.impl;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.github.gabrielgp0811.restlite.converter.impl.MapToTypeConverter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.service.TypedRestService;

/**
 * Main implementation of interface {@link TypedRestService}.
 * 
 * @author gabrielgp0811
 */
public class TypedRestServiceImpl<T> extends RestServiceImpl implements TypedRestService<T> {

	private Class<T> resultClass = null;

	/**
	 * 
	 */
	public TypedRestServiceImpl() {

	}

	/**
	 * @param resultClass The result class.
	 */
	public TypedRestServiceImpl(Class<T> resultClass) {
		this.resultClass = resultClass;
	}

	@Override
	public void setResultClass(Class<T> resultClass) {
		this.resultClass = resultClass;
	}

	@Override
	public T getSingleResult() throws RestException {
		List<T> resultList = getResultList();

		if (resultList.isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR, "REST Service has no result.");
		}

		if (resultList.size() > 1) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR, "REST Service has more than one result.");
		}

		return resultList.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getResultList() throws RestException {
		return super.getResultList().stream()
				.filter(obj -> Map.class.isAssignableFrom(obj.getClass()))
				.map(obj -> (Map<String, Object>) obj)
				.map(obj -> {
					try {
						return new MapToTypeConverter<T>(resultClass).convert(obj);
					} catch (RestException e) {
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

}