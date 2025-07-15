/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.github.gabrielgp0811.restlite.annotation.RestDateTimeFormat;
import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.util.Constants;
import io.github.gabrielgp0811.restlite.util.Util;

/**
 * Class responsible for converting object into {@link java.util.Map}.
 * 
 * @author gabrielgp0811
 */
public class TypeToMapConverter implements Converter<Object, Map<String, Object>> {

	/**
	 * 
	 */
	public TypeToMapConverter() {

	}

	@Override
	public Map<String, Object> convert(Object input) throws RestException {
		if (input == null) {
			return null;
		}

		Map<String, Object> result = new HashMap<>();

		Field[] fields = input.getClass().getDeclaredFields();

		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			String name = field.getName();
			Class<?> type = field.getType();

			String methodName = "get".concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));

			Method method = Arrays.stream(input.getClass().getDeclaredMethods())
					.filter(declaredMethod -> declaredMethod.getName().equals(methodName)).findFirst().orElse(null);

			if (method == null || method.getParameterTypes().length > 0) {
				continue;
			}

			Object value = null;

			try {
				value = method.invoke(input);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			}

			if (Util.isDate(type) || Util.isLocalDate(type) || Util.isLocalDateTime(type)
					|| Util.isLocalTime(type)) {
				String pattern = null;
				String locale = null;
				String timezone = null;

				if (field.isAnnotationPresent(RestDateTimeFormat.class)) {
					RestDateTimeFormat dateTimeFormat = field.getAnnotation(RestDateTimeFormat.class);

					pattern = dateTimeFormat.value();
					locale = dateTimeFormat.locale();
					timezone = dateTimeFormat.timezone();
				}

				if (Util.isDate(type)) {
					Date date = (Date) value;

					if (pattern == null || pattern.trim().isEmpty())
						pattern = Constants.DEFAULT_DATE_PATTERN;

					try {
						DateFormat formatter = null;

						if (locale != null && !locale.trim().isEmpty()) {
							formatter = new SimpleDateFormat(pattern, Util.toLocale(locale));
						} else {
							formatter = new SimpleDateFormat(pattern);
						}

						if (timezone != null && !timezone.trim().isEmpty()) {
							formatter.setTimeZone(Util.toTimeZone(timezone));
						}

						value = formatter.format(date);
					} catch (NullPointerException | IllegalArgumentException e) {
						value = date.toString();
					}
				}

				if (Util.isLocalDate(type)) {
					LocalDate localDate = (LocalDate) value;

					if (pattern == null || pattern.trim().isEmpty())
						pattern = Constants.DEFAULT_LOCALDATE_PATTERN;

					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

						if (locale != null && !locale.trim().isEmpty()) {
							formatter = formatter.withLocale(Util.toLocale(locale));
						}

						value = localDate.format(formatter);
					} catch (NullPointerException | DateTimeParseException e) {
						value = localDate.toString();
					}
				}

				if (Util.isLocalTime(type)) {
					LocalTime localTime = (LocalTime) value;

					if (pattern == null || pattern.trim().isEmpty())
						pattern = Constants.DEFAULT_LOCALTIME_PATTERN;

					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

						if (locale != null && !locale.trim().isEmpty()) {
							formatter = formatter.withLocale(Util.toLocale(locale));
						}

						value = localTime.format(formatter);
					} catch (NullPointerException | DateTimeParseException e) {
						value = localTime.toString();
					}
				}

				if (Util.isLocalDateTime(type)) {
					LocalDateTime localDateTime = (LocalDateTime) value;

					if (pattern == null || pattern.trim().isEmpty())
						pattern = Constants.DEFAULT_LOCALDATETIME_PATTERN;

					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

						if (locale != null && !locale.trim().isEmpty()) {
							formatter = formatter.withLocale(Util.toLocale(locale));
						}

						value = localDateTime.format(formatter);
					} catch (NullPointerException | DateTimeParseException e) {
						value = localDateTime.toString();
					}
				}
			}

			if (value == null) {
				result.put(name, null);
			} else if (Util.isDate(type)
					|| Util.isLocalDate(type)
					|| Util.isLocalDateTime(type)
					|| Util.isLocalTime(type)
					|| Util.isNumber(type)
					|| Util.isBoolean(type)
					|| Util.isCharacter(type)
					|| Util.isString(type)
					|| Util.isArray(type)
					|| Util.isCollection(type)
					|| Util.isEnum(type)
					|| Util.isMap(type)) {
				result.put(name, value);
			} else {
				result.put(name, convert(value));
			}
		}

		return result;
	}

}