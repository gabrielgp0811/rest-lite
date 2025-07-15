/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.DateTimeFormatTO;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;
import io.github.gabrielgp0811.restlite.util.Util;

/**
 * Class responsible for converting a
 * {@link io.github.gabrielgp0811.restlite.to.RestServiceParameterTO} object
 * into {@link java.util.Map}.
 * 
 * @author gabrielgp0811
 */
public class RestServiceParameterToMapConverter
		implements Converter<RestServiceParameterTO, Map<String, Object>> {

	/**
	 * 
	 */
	public RestServiceParameterToMapConverter() {

	}

	@Override
	public Map<String, Object> convert(RestServiceParameterTO input) throws RestException {
		if (input == null || input.getName().trim().isEmpty()) {
			return null;
		}

		return toMap(input.getName(), input.getValue(), input.getDateTimeFormat());
	}

	private Map<String, Object> toMap(String name, Object obj, DateTimeFormatTO dateTimeFormat) {
		Map<String, Object> result = new HashMap<>();

		if (Util.isDate(obj) || Util.isLocalDate(obj) || Util.isLocalDateTime(obj)
				|| Util.isLocalTime(obj)) {
			String pattern = dateTimeFormat.getPattern();
			String locale = dateTimeFormat.getLocale();
			String timezone = dateTimeFormat.getTimezone();

			if (Util.isDate(obj)) {
				Date date = (Date) obj;

				if (pattern.trim().isEmpty())
					pattern = "EEE MMM dd HH:mm:ss zzz yyyy";

				try {
					DateFormat formatter = null;

					if (!locale.trim().isEmpty()) {
						formatter = new SimpleDateFormat(pattern, Util.toLocale(locale));
					} else {
						formatter = new SimpleDateFormat(pattern);
					}

					if (!timezone.trim().isEmpty()) {
						formatter.setTimeZone(Util.toTimeZone(timezone));
					}

					result.put(name, formatter.format(date));
				} catch (IllegalArgumentException e) {
					try {
						result.put(name, new SimpleDateFormat(pattern).format(date));
					} catch (IllegalArgumentException e1) {
						result.put(name, date);
					}
				}
			}

			if (Util.isLocalDate(obj)) {
				LocalDate localDate = (LocalDate) obj;

				if (pattern.trim().isEmpty())
					pattern = "uuuu-MM-dd";

				try {
					DateTimeFormatter formatter = null;

					if (!locale.trim().isEmpty()) {
						formatter = DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale));
					} else {
						formatter = DateTimeFormatter.ofPattern(pattern);
					}

					result.put(name, localDate.format(formatter));
				} catch (DateTimeParseException e) {
					result.put(name, localDate);
				}
			}

			if (Util.isLocalTime(obj)) {
				LocalTime localTime = (LocalTime) obj;

				if (pattern.trim().isEmpty())
					pattern = "HH:mm:ss";

				try {
					DateTimeFormatter formatter = null;

					if (!locale.trim().isEmpty()) {
						formatter = DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale));
					} else {
						formatter = DateTimeFormatter.ofPattern(pattern);
					}

					result.put(name, localTime.format(formatter));
				} catch (DateTimeParseException e) {
					result.put(name, localTime);
				}
			}

			if (Util.isLocalDateTime(obj)) {
				LocalDateTime localDateTime = (LocalDateTime) obj;

				if (pattern.trim().isEmpty())
					pattern = "yyyy-MM-dd'T'HH:mm:ss";

				try {
					DateTimeFormatter formatter = null;

					if (!locale.trim().isEmpty()) {
						formatter = DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale));
					} else {
						formatter = DateTimeFormatter.ofPattern(pattern);
					}

					result.put(name, localDateTime.format(formatter));
				} catch (DateTimeParseException e) {
					result.put(name, localDateTime);
				}
			}
		} else if (Util.isArray(obj) || Util.isCollection(obj)) {
			if (Util.isArray(obj)) {
				int length = Array.getLength(obj);
				Object[] array = new Object[length];

				for (int i = 0; i < length; i++) {
					Object arrayValue = Array.get(obj, i);

					if (Util.isDate(arrayValue)) {
						Date date = (Date) arrayValue;
						String pattern = dateTimeFormat.getPattern();
						String locale = dateTimeFormat.getLocale();
						String timezone = dateTimeFormat.getTimezone();

						if (pattern.trim().isEmpty())
							pattern = "EEE MMM dd HH:mm:ss zzz yyyy";

						try {
							DateFormat formatter = null;

							if (!locale.trim().isEmpty()) {
								formatter = new SimpleDateFormat(pattern, Util.toLocale(locale));
							} else {
								formatter = new SimpleDateFormat(pattern);
							}

							if (!timezone.trim().isEmpty()) {
								formatter.setTimeZone(Util.toTimeZone(timezone));
							}

							arrayValue = formatter.format(date);
						} catch (IllegalArgumentException e) {
							try {
								arrayValue = new SimpleDateFormat(pattern).format(date);
							} catch (IllegalArgumentException e1) {
								arrayValue = date;
							}
						}
					}

					if (Util.isLocalDate(arrayValue)) {
						LocalDate localDate = (LocalDate) arrayValue;
						String pattern = dateTimeFormat.getPattern();
						String locale = dateTimeFormat.getLocale();

						if (pattern.trim().isEmpty())
							pattern = "uuuu-MM-dd";

						try {
							DateTimeFormatter formatter = null;

							if (!locale.trim().isEmpty()) {
								formatter = DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale));
							} else {
								formatter = DateTimeFormatter.ofPattern(pattern);
							}

							arrayValue = localDate.format(formatter);
						} catch (DateTimeParseException e) {
							arrayValue = localDate;
						}
					}

					if (Util.isLocalTime(arrayValue)) {
						LocalTime localTime = (LocalTime) arrayValue;
						String pattern = dateTimeFormat.getPattern();
						String locale = dateTimeFormat.getLocale();

						if (pattern.trim().isEmpty())
							pattern = "HH:mm:ss";

						try {
							DateTimeFormatter formatter = null;

							if (!locale.trim().isEmpty()) {
								formatter = DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale));
							} else {
								formatter = DateTimeFormatter.ofPattern(pattern);
							}

							arrayValue = localTime.format(formatter);
						} catch (DateTimeParseException e) {
							arrayValue = localTime;
						}
					}

					if (Util.isLocalDateTime(arrayValue)) {
						LocalDateTime localDateTime = (LocalDateTime) arrayValue;
						String pattern = dateTimeFormat.getPattern();
						String locale = dateTimeFormat.getLocale();

						if (pattern.trim().isEmpty())
							pattern = "yyyy-MM-dd'T'HH:mm:ss";

						try {
							DateTimeFormatter formatter = null;

							if (!locale.trim().isEmpty()) {
								formatter = DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale));
							} else {
								formatter = DateTimeFormatter.ofPattern(pattern);
							}

							arrayValue = localDateTime.format(formatter);
						} catch (DateTimeParseException e) {
							arrayValue = localDateTime;
						}
					}

					if (!Util.isNumber(arrayValue)
							&& !Util.isBoolean(arrayValue)
							&& !Util.isCharacter(arrayValue)
							&& !Util.isString(arrayValue)
							&& !Util.isEnum(arrayValue)
							&& !Util.isMap(arrayValue)) {
						try {
							arrayValue = new TypeToMapConverter().convert(arrayValue);
						} catch (RestException e) {
						}
					}

					array[i] = arrayValue;
				}

				result.put(name, array);
			}
		} else if (!Util.isNumber(obj)
				&& !Util.isBoolean(obj)
				&& !Util.isCharacter(obj)
				&& !Util.isString(obj)
				&& !Util.isEnum(obj)
				&& !Util.isMap(obj)) {
			try {
				result.put(name, new TypeToMapConverter().convert(obj));
			} catch (RestException e) {
			}
		} else {
			result.put(name, obj);
		}

		return result;
	}
}