/**
 * 
 */
package io.github.gabrielgp0811.restlite.to;

import java.io.Serializable;

/**
 * @author gabrielgp0811
 */
public class RestServiceParameterTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1597513988829640784L;

	private String name = null;

	private Object value = null;

	private Class<?> type = null;

	private DateTimeFormatTO dateTimeFormat = null;

	private boolean optional = false;

	/**
	 * 
	 */
	public RestServiceParameterTO() {

	}

	/**
	 * @param name  the name
	 * @param value the value
	 */
	public RestServiceParameterTO(String name, Object value) {
		this.name = name;
		this.value = value;
		this.type = value == null ? null : value.getClass();
	}

	/**
	 * @param name           the name
	 * @param value          the value
	 * @param dateTimeFormat the dateTimeFormat
	 */
	public RestServiceParameterTO(String name, Object value, DateTimeFormatTO dateTimeFormat) {
		this.name = name;
		this.value = value;
		this.type = value == null ? null : value.getClass();
		this.dateTimeFormat = dateTimeFormat;
	}

	/**
	 * @param name           the name
	 * @param type           the type
	 * @param dateTimeFormat the dateTimeFormat
	 * @param optional       the optional
	 */
	public RestServiceParameterTO(String name, Class<?> type, DateTimeFormatTO dateTimeFormat, boolean optional) {
		this.name = name;
		this.type = type;
		this.dateTimeFormat = dateTimeFormat;
		this.optional = optional;
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
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		if (type == null) {
			type = Object.class;
		}
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * @return the dateTimeFormat
	 */
	public DateTimeFormatTO getDateTimeFormat() {
		if (dateTimeFormat == null) {
			dateTimeFormat = new DateTimeFormatTO();
		}
		return dateTimeFormat;
	}

	/**
	 * @param dateTimeFormat the dateTimeFormat to set
	 */
	public void setDateTimeFormat(DateTimeFormatTO dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	/**
	 * @return the optional
	 */
	public boolean isOptional() {
		return optional;
	}

	/**
	 * @param optional the optional to set
	 */
	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	@Override
	public String toString() {
		return "RestServiceParameterTO [name=" + name + ", value=" + value + ", type=" + type + ", dateTimeFormat="
				+ dateTimeFormat + ", optional=" + optional + "]";
	}

}