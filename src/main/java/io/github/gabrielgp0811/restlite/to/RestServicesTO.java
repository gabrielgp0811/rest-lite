/**
 * 
 */
package io.github.gabrielgp0811.restlite.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gabrielgp0811
 */
public class RestServicesTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1146575569381501919L;

	private List<RestServiceTO> services = null;

	/**
	 * 
	 */
	public RestServicesTO() {

	}

	/**
	 * @param services the services to set
	 */
	public RestServicesTO(List<RestServiceTO> services) {
		this.services = services;
	}

	/**
	 * @return the services
	 */
	public List<RestServiceTO> getServices() {
		if (services == null) {
			services = new ArrayList<>();
		}
		return services;
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(List<RestServiceTO> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "RestServicesTO [services=" + Arrays.toString(getServices().toArray(new RestServiceTO[0])) + "]";
	}

}