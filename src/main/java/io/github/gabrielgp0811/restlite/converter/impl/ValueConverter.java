/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.time.DateTimeException;

import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;
import io.github.gabrielgp0811.restlite.util.Util;

/**
 * Class responsible for converting value contained in
 * {@link RestServiceParameterTO} object into a <code>java.lang.String</code>.
 * 
 * @author gabrielgp0811
 */
public class ValueConverter implements Converter<RestServiceParameterTO, String> {

	/**
	 * 
	 */
	public ValueConverter() {

	}

	@Override
	public String convert(RestServiceParameterTO input) throws RestException {
		if (input == null || input.getValue() == null) {
			return "";
		}

		Object value = input.getValue();
		String pattern = input.getDateTimeFormat().getPattern();
		String locale = input.getDateTimeFormat().getLocale();
		String timezone = input.getDateTimeFormat().getTimezone();

		try {
			if (Util.isDate(value)) {
				if (pattern.trim().isEmpty() && locale.trim().isEmpty() && timezone.trim().isEmpty()) {
					value = ((java.util.Date) value).toString();
				} else {
					if (pattern.trim().isEmpty()) {
						pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
					}

					java.text.DateFormat formatter = null;

					if (locale.trim().isEmpty()) {
						formatter = new java.text.SimpleDateFormat(pattern);
					} else {
						formatter = new java.text.SimpleDateFormat(pattern, Util.toLocale(locale));
					}

					if (!timezone.trim().isEmpty())
						formatter.setTimeZone(Util.toTimeZone(timezone));

					value = formatter.format((java.util.Date) value);
				}
			} else if (Util.isLocalDate(value)) {
				if (locale.trim().isEmpty()) {
					if (pattern.trim().isEmpty()) {
						value = ((java.time.LocalDate) value).toString();
					} else {
						value = ((java.time.LocalDate) value)
								.format(java.time.format.DateTimeFormatter.ofPattern(pattern));
					}
				} else {
					if (pattern.trim().isEmpty()) {
						pattern = "uuuu-MM-dd";
					}

					value = ((java.time.LocalDate) value)
							.format(java.time.format.DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale)));
				}
			} else if (Util.isLocalTime(value)) {
				if (locale.trim().isEmpty()) {
					if (pattern.trim().isEmpty()) {
						value = ((java.time.LocalTime) value).toString();
					} else {
						value = ((java.time.LocalTime) value)
								.format(java.time.format.DateTimeFormatter.ofPattern(pattern));
					}
				} else {
					if (pattern.trim().isEmpty()) {
						pattern = "HH:mm:ss";
					}

					value = ((java.time.LocalTime) value)
							.format(java.time.format.DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale)));
				}
			} else if (Util.isLocalDateTime(value)) {
				if (locale.trim().isEmpty()) {
					if (pattern.trim().isEmpty()) {
						value = ((java.time.LocalDateTime) value).toString();
					} else {
						value = ((java.time.LocalDateTime) value)
								.format(java.time.format.DateTimeFormatter.ofPattern(pattern));
					}
				} else {
					if (pattern.trim().isEmpty()) {
						pattern = "uuuu-MM-dd'T'HH:mm:ss";
					}

					value = ((java.time.LocalDateTime) value)
							.format(java.time.format.DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale)));
				}
			}
		} catch (IllegalArgumentException | DateTimeException e) {
		}

		return value.toString().trim();
	}

}