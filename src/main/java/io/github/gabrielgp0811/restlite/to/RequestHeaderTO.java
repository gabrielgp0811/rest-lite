/**
 * 
 */
package io.github.gabrielgp0811.restlite.to;

import java.io.Serializable;

/**
 * @author gabrielgp0811
 */
public class RequestHeaderTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2087213232697985894L;

	private String name = null;

	private String value = null;

	/**
	 * 
	 */
	public RequestHeaderTO() {

	}

	/**
	 * @param name  the name to set
	 * @param value the value to set
	 */
	public RequestHeaderTO(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		if (name == null) {
			name = "";
		}
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		if (value == null) {
			value = "";
		}
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "RequestHeaderTO [name=" + name + ", value=" + value + "]";
	}

}