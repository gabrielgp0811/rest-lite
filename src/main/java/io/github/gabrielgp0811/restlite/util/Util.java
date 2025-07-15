/**
 * 
 */
package io.github.gabrielgp0811.restlite.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Util class.
 * 
 * @author gabrielgp0811
 */
public final class Util {

	/**
	 * 
	 */
	public Util() {

	}

	/**
	 * Checks if <code>clazz</code> is an array.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is an
	 *         array; <strong><span style="color:#7f0055">false</span></strong>
	 *         otherwise.
	 */
	public static boolean isArray(Class<?> clazz) {
		return clazz != null && clazz.isArray();
	}

	/**
	 * Checks if <code>obj</code> is an array.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is an
	 *         array; <strong><span style="color:#7f0055">false</span></strong>
	 *         otherwise.
	 */
	public static boolean isArray(Object obj) {
		if (obj != null && isArray(obj.getClass())) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.math.BigDecimal</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.math.BigDecimal</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isBigDecimal(Class<?> clazz) {
		return clazz != null && BigDecimal.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.math.BigDecimal</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.math.BigDecimal</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isBigDecimal(Object obj) {
		if (obj instanceof BigDecimal) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.math.BigDecimal</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.math.BigInteger</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isBigInteger(Class<?> clazz) {
		return clazz != null && BigInteger.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.math.BigInteger</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.math.BigInteger</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isBigInteger(Object obj) {
		if (obj instanceof BigInteger) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.lang.Boolean</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Boolean</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isBoolean(Class<?> clazz) {
		return clazz != null && (Boolean.TYPE.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.lang.Boolean</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Boolean</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isBoolean(Object obj) {
		if ((obj instanceof Boolean) || (obj != null && obj.getClass() == Boolean.TYPE)) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.lang.Byte</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Byte</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isByte(Class<?> clazz) {
		return clazz != null && (Byte.TYPE.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.lang.Byte</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Byte</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isByte(Object obj) {
		if (obj != null && isByte(obj.getClass())) {
			return true;
		}

		return obj instanceof Byte;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.lang.Character</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Character</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isCharacter(Class<?> clazz) {
		return clazz != null && (Character.TYPE.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.lang.Character</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Character</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isCharacter(Object obj) {
		if (obj != null && (obj instanceof Character || isCharacter(obj.getClass()))) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se a classe <code>clazz</code> &eacute; representa uma lista
	 * (ArrayList, Vector, etc).
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.util.Collection</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isCollection(Class<?> clazz) {
		return clazz != null && Collection.class.isAssignableFrom(clazz);
	}

	/**
	 * Verifica se o objeto <code>obj</code> &eacute; um array.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.util.Collection</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isCollection(Object obj) {
		if (obj != null && obj instanceof Collection) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.util.Date</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.util.Date</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isDate(Class<?> clazz) {
		return clazz != null && Date.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.util.Date</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.util.Date</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isDate(Object obj) {
		return obj != null && obj instanceof Date;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.lang.Double</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Double</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isDouble(Class<?> clazz) {
		return clazz != null && (Double.TYPE.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.lang.Double</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Double</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isDouble(Object obj) {
		if (obj != null && isDouble(obj.getClass())) {
			return true;
		}

		return obj instanceof Double;
	}

	/**
	 * Checks if <code>clazz</code> is a
	 * <strong><span style="color:#7f0055">enum</span></strong>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is an
	 *         <strong><span style="color:#7f0055">enum</span></strong>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isEnum(Class<?> clazz) {
		return clazz != null && (clazz.isEnum() || Enum.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is an
	 * <strong><span style="color:#7f0055">enum</span></strong>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is an
	 *         <strong><span style="color:#7f0055">enum</span></strong>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isEnum(Object obj) {
		return obj != null && obj instanceof Enum;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.lang.Float</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Float</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isFloat(Class<?> clazz) {
		return clazz != null && (Float.TYPE.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.lang.Float</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Float</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isFloat(Object obj) {
		if (obj != null && isFloat(obj.getClass())) {
			return true;
		}

		return obj instanceof Float;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.lang.Integer</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Integer</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isInteger(Class<?> clazz) {
		return clazz != null && (Integer.TYPE.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.lang.Integer</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Integer</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isInteger(Object obj) {
		if (obj != null && isInteger(obj.getClass())) {
			return true;
		}

		return obj instanceof Integer;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.time.LocalDate</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.time.LocalDate</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isLocalDate(Class<?> clazz) {
		return clazz != null && LocalDate.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.time.LocalDate</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.time.LocalDate</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isLocalDate(Object obj) {
		if (obj != null && isLocalDate(obj.getClass())) {
			return true;
		}

		return obj instanceof LocalDate;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.time.LocalDateTime</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.time.LocalDateTime</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isLocalDateTime(Class<?> clazz) {
		return clazz != null && LocalDateTime.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.time.LocalDateTime</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.time.LocalDateTime</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isLocalDateTime(Object obj) {
		if (obj != null && isLocalDateTime(obj.getClass())) {
			return true;
		}

		return obj instanceof LocalDateTime;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.time.LocalTime</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.time.LocalTime</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isLocalTime(Class<?> clazz) {
		return clazz != null && LocalTime.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.time.LocalTime</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.time.LocalTime</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isLocalTime(Object obj) {
		if (obj != null && isLocalTime(obj.getClass())) {
			return true;
		}

		return obj instanceof LocalTime;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.lang.Long</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Long</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isLong(Class<?> clazz) {
		return clazz != null && (Long.TYPE.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.lang.Long</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Long</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isLong(Object obj) {
		if (obj != null && isLong(obj.getClass())) {
			return true;
		}

		return obj instanceof Long;
	}

	/**
	 * Verifica se a classe <code>clazz</code> representa um
	 * <code>java.util.Map</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.util.Map</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isMap(Class<?> clazz) {
		return clazz != null && Map.class.isAssignableFrom(clazz);
	}

	/**
	 * Verifica se o objeto <code>obj</code> representa um
	 * <code>java.util.Map</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.util.Map</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isMap(Object obj) {
		if (obj != null && isMap(obj.getClass())) {
			return true;
		}

		return obj instanceof Map;
	}

	/**
	 * Verifica se a classe <code>clazz</code> representa uma classe
	 * num&eacute;rica.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         number; <strong><span style="color:#7f0055">false</span></strong>
	 *         otherwise.
	 */
	public static boolean isNumber(Class<?> clazz) {
		return clazz != null && (isByte(clazz) || isShort(clazz) || isInteger(clazz) || isLong(clazz) || isFloat(clazz)
				|| isDouble(clazz) || isBigDecimal(clazz) || isBigInteger(clazz)
				|| Number.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is a number.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         number; <strong><span style="color:#7f0055">false</span></strong>
	 *         otherwise.
	 */
	public static boolean isNumber(Object obj) {
		if (obj != null && isNumber(obj.getClass())) {
			return true;
		}

		return obj instanceof Number;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.lang.Short</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Short</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isShort(Class<?> clazz) {
		return clazz != null && (Short.TYPE.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz));
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.lang.Short</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.Short</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isShort(Object obj) {
		if (obj != null && isShort(obj.getClass())) {
			return true;
		}

		return obj instanceof Short;
	}

	/**
	 * Checks if <code>clazz</code> is a <code>java.lang.String</code>.
	 * 
	 * @param clazz The class.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.String</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isString(Class<?> clazz) {
		return clazz != null && String.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if <code>obj</code> is a <code>java.lang.String</code>.
	 * 
	 * @param obj The object.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is a
	 *         <code>java.lang.String</code>;
	 *         <strong><span style="color:#7f0055">false</span></strong> otherwise.
	 */
	public static boolean isString(Object obj) {
		if (obj != null && (obj instanceof String || isString(obj.getClass()))) {
			return true;
		}

		return false;
	}

	/**
	 * Set the <code>value</code> in field <code>fieldName</code> of type
	 * <code>fieldType</code> in object <code>obj</code>.
	 * 
	 * @param obj       The object.
	 * @param fieldName The field name.
	 * @param fieldType The field type.
	 * @param value     The value.
	 */
	public static void setValue(Object obj, String fieldName, Class<?> fieldType, Object value) {
		if (obj == null || fieldName == null || fieldName.trim().isEmpty() || fieldType == null || value == null) {
			return;
		}

		try {
			Method method = obj.getClass().getDeclaredMethod(
					"set".concat(fieldName.substring(0, 1).toUpperCase()).concat(fieldName.substring(1)), fieldType);

			method.invoke(obj, value);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
		}
	}

	/**
	 * @param locale The locale.
	 * @return The <code>java.util.Locale</code>.
	 */
	public static Locale toLocale(String locale) {
		if (locale == null || locale.trim().isEmpty()) {
			return null;
		}

		String locale0 = locale.trim().intern();

		int len = locale0.length();
		if (len != 2 && len != 5 && len < 7) {
			return null;
		}

		char ch0 = locale0.charAt(0);
		char ch1 = locale0.charAt(1);

		if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
			return null;
		}

		if (len == 2)
			return new Locale(locale0, "");

		if (locale0.charAt(2) != '_') {
			return null;
		}

		char ch3 = locale0.charAt(3);
		if (ch3 == '_')
			return new Locale(locale0.substring(0, 2), "", locale0.substring(4));

		char ch4 = locale0.charAt(4);
		if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
			return null;
		}

		if (len == 5)
			return new Locale(locale0.substring(0, 2), locale0.substring(3, 5));

		if (locale0.charAt(5) != '_') {
			return null;
		}

		return new Locale(locale0.substring(0, 2), locale0.substring(3, 5), locale0.substring(6));
	}

	/**
	 * @param timezone The ID for a TimeZone.
	 * @return The <code>java.util.TimeZone</code>.
	 */
	public static TimeZone toTimeZone(String timezone) {
		if (timezone == null || timezone.trim().isEmpty()) {
			return null;
		}

		return TimeZone.getTimeZone(timezone.trim());
	}

}