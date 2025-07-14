/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.github.gabrielgp0811.jsonlite.annotation.JsonPattern;
import io.github.gabrielgp0811.jsonlite.util.Util;
import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;

/**
 * Class responsible for converting {@link java.util.Map} into object of type
 * <code>&lt;T&gt;</code>
 * 
 * @param <T> The generic class.
 * @author gabrielgp0811
 */
public class MapToTypeConverter<T> implements Converter<Map<String, Object>, T> {

	private Class<T> type = null;

	/**
	 * @param type Generic type for output.
	 */
	public MapToTypeConverter(Class<T> type) {
		this.type = type;
	}

	@Override
	public T convert(Map<String, Object> input) throws RestException {
		if (input == null) {
			return null;
		}

		T result = null;
		try {
			result = type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR,
					"Error instantiating class '" + type.getName() + "'.");
		}

		Field[] fields = type.getDeclaredFields();

		Set<Entry<String, Object>> entrySet = input.entrySet();

		for (Entry<String, Object> entry : entrySet) {
			for (Field field : fields) {
				String name = entry.getKey();

				if (Modifier.isStatic(field.getModifiers()) || !field.getName().equals(name)) {
					continue;
				}

				String methodName = "set".concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));

				Method method = Arrays.stream(type.getDeclaredMethods())
						.filter(declaredMethod -> declaredMethod.getName().equals(methodName)).findFirst().orElse(null);

				if (method == null || method.getParameterTypes().length != 1) {
					continue;
				}

				Object value = entry.getValue();

				Class<?> parameterType = method.getParameterTypes()[0];

				if (Util.isDate(parameterType) || Util.isLocalDate(parameterType) || Util.isLocalDateTime(parameterType)
						|| Util.isLocalTime(parameterType)) {
					String date = (String) value;

					String pattern = null;
					String locale = null;
					String timezone = null;

					if (field.isAnnotationPresent(JsonPattern.class)) {
						JsonPattern dateTimeFormat = field.getAnnotation(JsonPattern.class);

						pattern = dateTimeFormat.value();
						locale = dateTimeFormat.locale();
						timezone = dateTimeFormat.timezone();
					}

					if (Util.isDate(parameterType)) {
						if (pattern == null || pattern.trim().isEmpty())
							pattern = "EEE MMM dd HH:mm:ss zzz yyyy";

						try {
							DateFormat formatter = null;

							if (locale != null) {
								formatter = new SimpleDateFormat(pattern, Util.toLocale(locale));
							} else {
								formatter = new SimpleDateFormat(pattern);
							}

							if (timezone != null) {
								formatter.setTimeZone(Util.toTimeZone(timezone));
							}

							value = formatter.parse(date);
						} catch (NullPointerException | ParseException | IllegalArgumentException e) {
							try {
								value = new SimpleDateFormat(pattern).parse(date);
							} catch (ParseException e1) {
							}
						}
					}

					if (Util.isLocalDate(parameterType)) {
						if (pattern == null || pattern.trim().isEmpty())
							pattern = "yyyy-MM-dd";

						try {
							DateTimeFormatter formatter = null;

							if (locale != null) {
								formatter = DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale));
							} else {
								formatter = DateTimeFormatter.ofPattern(pattern);
							}

							value = LocalDate.parse(date, formatter);
						} catch (NullPointerException | DateTimeParseException e) {
							try {
								value = LocalDate.parse(date);
							} catch (DateTimeParseException e1) {
							}
						}
					}

					if (Util.isLocalTime(parameterType)) {
						if (pattern == null || pattern.trim().isEmpty())
							pattern = "HH:mm:ss";

						try {
							DateTimeFormatter formatter = null;

							if (locale != null) {
								formatter = DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale));
							} else {
								formatter = DateTimeFormatter.ofPattern(pattern);
							}

							value = LocalDate.parse(date, formatter);
						} catch (NullPointerException | DateTimeParseException e) {
							try {
								value = LocalDate.parse(date);
							} catch (DateTimeParseException e1) {
							}
						}
					}

					if (Util.isLocalDateTime(parameterType)) {
						if (pattern == null || pattern.trim().isEmpty())
							pattern = "yyyy-MM-dd'T'HH:mm:ss";

						try {
							DateTimeFormatter formatter = null;

							if (locale != null) {
								formatter = DateTimeFormatter.ofPattern(pattern, Util.toLocale(locale));
							} else {
								formatter = DateTimeFormatter.ofPattern(pattern);
							}

							value = LocalDate.parse(date, formatter);
						} catch (NullPointerException | DateTimeParseException e) {
							try {
								value = LocalDate.parse(date);
							} catch (DateTimeParseException e1) {
							}
						}
					}
				}

				if (Util.isNumber(parameterType) && Util.isNumber(value)) {
					if (Util.isByte(parameterType)) {
						value = ((Number) value).byteValue();
					} else if (Util.isShort(parameterType)) {
						value = ((Number) value).shortValue();
					} else if (Util.isInteger(parameterType)) {
						value = ((Number) value).intValue();
					} else if (Util.isLong(parameterType)) {
						value = ((Number) value).longValue();
					} else if (Util.isFloat(parameterType)) {
						value = ((Number) value).floatValue();
					} else if (Util.isDouble(parameterType)) {
						value = ((Number) value).doubleValue();
					} else if (Util.isBigDecimal(parameterType)) {
						if (Util.isByte(value) || Util.isShort(value) || Util.isLong(value)) {
							value = new BigDecimal((long) value);
						} else if (Util.isInteger(value)) {
							value = new BigDecimal((int) value);
						} else if (Util.isFloat(value) || Util.isDouble(value)) {
							value = new BigDecimal((double) value);
						} else if (Util.isBigInteger(value)) {
							value = new BigDecimal((BigInteger) value);
						}
					} else if (Util.isBigInteger(parameterType)) {
						if (Util.isByte(value) || Util.isShort(value) || Util.isInteger(value) || Util.isLong(value)) {
							value = BigInteger.valueOf((long) value);
						} else if (Util.isFloat(value) || Util.isDouble(value)) {
							value = new BigInteger(String.valueOf(value));
						} else if (Util.isBigDecimal(value)) {
							value = ((BigDecimal) value).toBigInteger();
						}
					}
				}

				try {
					method.invoke(result, value);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				}
			}
		}

		return result;
	}

}