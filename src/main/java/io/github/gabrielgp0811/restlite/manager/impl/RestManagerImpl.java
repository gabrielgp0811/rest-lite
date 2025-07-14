/**
 * 
 */
package io.github.gabrielgp0811.restlite.manager.impl;

import java.util.List;
import java.util.Map;

import io.github.gabrielgp0811.restlite.manager.RestManager;
import io.github.gabrielgp0811.restlite.service.impl.RestServiceImpl;
import io.github.gabrielgp0811.restlite.service.impl.TypedRestServiceImpl;

/**
 * The main implementation of abstract class {@link RestManager}.
 * 
 * @author gabrielgp0811
 */
public class RestManagerImpl extends RestManager {

	/**
	 * @param packageNames   The name of the packages where the classes containing
	 *                       REST Services annotations are mapped.
	 * @param defaultHeaders The default headers for every service created.
	 */
	public RestManagerImpl(List<String> packageNames, Map<String, String> defaultHeaders) {
		super(packageNames, defaultHeaders);

		this.restServiceImplClass = RestServiceImpl.class;
		this.restTypedRestServiceImplClass = TypedRestServiceImpl.class;
	}

}