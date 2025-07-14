/**
 * 
 */
package io.github.gabrielgp0811.restlite.to;

import java.io.Serializable;

/**
 * @author gabrielgp0811
 */
public class DateTimeFormatTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2087213232697985894L;

	private String pattern = null;

	private String locale = null;

	private String timezone = null;

	public DateTimeFormatTO() {

	}

	public DateTimeFormatTO(String pattern) {
		this.pattern = pattern;
	}

	public DateTimeFormatTO(String pattern, String locale, String timezone) {
		this.pattern = pattern;
		this.locale = locale;
		this.timezone = timezone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPattern() {
		if (pattern == null) {
			pattern = "";
		}
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getLocale() {
		if (locale == null) {
			locale = "";
		}
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimezone() {
		if (timezone == null) {
			timezone = "";
		}
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	@Override
	public String toString() {
		return "DateTimeFormatTO [pattern=" + pattern + ", locale=" + locale + ", timezone=" + timezone + "]";
	}

}