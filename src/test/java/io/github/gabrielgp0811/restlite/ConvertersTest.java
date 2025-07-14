/**
 * 
 */
package io.github.gabrielgp0811.restlite;

import java.util.Arrays;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.gabrielgp0811.restlite.converter.impl.FormUrlEncodedConverter;
import io.github.gabrielgp0811.restlite.converter.impl.JsonConverter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.DateTimeFormatTO;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;

/**
 * @author gabrielgp0811
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConvertersTest {

	private static final Logger LOGGER = Logger.getLogger(UserConsumerTest.class.getName());

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		LogManager.getLogManager().readConfiguration();
	}

	/**
	 * Test method for
	 * {@link io.github.gabrielgp0811.restlite.converter.impl.FormUrlEncodedConverter#convert(com.github.gabrielgp0811.restlite.model.RestServiceParameterTO[])}.
	 * 
	 * @throws RestException
	 */
	@Test
	@Order(1)
	public void testFormUrlEncodedConvert() throws RestException {
		LOGGER.info(new FormUrlEncodedConverter().convert(Arrays.asList(
				new RestServiceParameterTO("test", "test123", String.class),
				new RestServiceParameterTO("number", 123, Integer.class),
				new RestServiceParameterTO("conditionTrue", true, Boolean.class),
				new RestServiceParameterTO("conditionFalse", false, Boolean.class),
				new RestServiceParameterTO("stringArray", new String[] { "first", "second", "third" }, String[].class),
				new RestServiceParameterTO("dateArray1", new java.time.LocalDate[] { java.time.LocalDate.now(), java.time.LocalDate.of(1990, 1, 20) }, java.time.LocalDate[].class),
				new RestServiceParameterTO("dateArray2", new java.time.LocalDate[] { java.time.LocalDate.now(), java.time.LocalDate.of(1990, 1, 20) }, java.time.LocalDate[].class, new DateTimeFormatTO("MM/dd/yyyy")),
				new RestServiceParameterTO("date1", new java.util.Date(), java.util.Date.class),
				new RestServiceParameterTO("date2", new java.util.Date(), java.util.Date.class, new DateTimeFormatTO("yyyy-MM-dd")),
				new RestServiceParameterTO("localDate1", java.time.LocalDate.now(), java.time.LocalDate.class),
				new RestServiceParameterTO("localDate2", java.time.LocalDate.now(), java.time.LocalDate.class, new DateTimeFormatTO("MM/dd")),
				new RestServiceParameterTO("localTime1", java.time.LocalTime.now(), java.time.LocalTime.class),
				new RestServiceParameterTO("localTime2", java.time.LocalTime.now(), java.time.LocalTime.class, new DateTimeFormatTO("hh:mm a")),
				new RestServiceParameterTO("localDateTime1", java.time.LocalDateTime.now(), java.time.LocalDateTime.class),
				new RestServiceParameterTO("localDateTime2", java.time.LocalDateTime.now(), java.time.LocalDateTime.class, new DateTimeFormatTO("MM/dd/yyyy hh:mm:ss.SSS a")),
				new RestServiceParameterTO("localDateTime2", java.time.LocalDateTime.now(), java.time.LocalDateTime.class, new DateTimeFormatTO("yyyy-MM-dd'T'HH:mm:ss.SSS")),
				new RestServiceParameterTO("user.id", 123, Integer.class),
				new RestServiceParameterTO("user.username", "user123", String.class)
		)));
	}

	/**
	 * Test method for
	 * {@link io.github.gabrielgp0811.restlite.converter.impl.JsonConverter#convert(com.github.gabrielgp0811.restlite.model.RestServiceParameterTO[])}.
	 * 
	 * @throws RestException
	 */
	@Test
	@Order(2)
	public void testJsonConvert() throws RestException {
		LOGGER.info(new JsonConverter().convert(Arrays.asList(
				new RestServiceParameterTO("test", "test123", String.class),
				new RestServiceParameterTO("number", 123, Integer.class),
				new RestServiceParameterTO("conditionTrue", true, Boolean.class),
				new RestServiceParameterTO("conditionFalse", false, Boolean.class),
				new RestServiceParameterTO("stringArray", new String[] { "first", "second", "third" }, String[].class),
				new RestServiceParameterTO("dateArray1", new java.time.LocalDate[] { java.time.LocalDate.now(), java.time.LocalDate.of(1990, 1, 20) }, java.time.LocalDate[].class),
				new RestServiceParameterTO("dateArray2", new java.time.LocalDate[] { java.time.LocalDate.now(), java.time.LocalDate.of(1990, 1, 20) }, java.time.LocalDate[].class, new DateTimeFormatTO("MM/dd/yyyy")),
				new RestServiceParameterTO("date1", new java.util.Date(), java.util.Date.class),
				new RestServiceParameterTO("date2", new java.util.Date(), java.util.Date.class, new DateTimeFormatTO("yyyy-MM-dd")),
				new RestServiceParameterTO("localDate1", java.time.LocalDate.now(), java.time.LocalDate.class),
				new RestServiceParameterTO("localDate2", java.time.LocalDate.now(), java.time.LocalDate.class, new DateTimeFormatTO("MM/dd")),
				new RestServiceParameterTO("localTime1", java.time.LocalTime.now(), java.time.LocalTime.class),
				new RestServiceParameterTO("localTime2", java.time.LocalTime.now(), java.time.LocalTime.class, new DateTimeFormatTO("hh:mm a")),
				new RestServiceParameterTO("localDateTime1", java.time.LocalDateTime.now(), java.time.LocalDateTime.class),
				new RestServiceParameterTO("localDateTime2", java.time.LocalDateTime.now(), java.time.LocalDateTime.class, new DateTimeFormatTO("MM/dd/uuuu hh:mm:ss.SSS a")),
				new RestServiceParameterTO("localDateTime2", java.time.LocalDateTime.now(), java.time.LocalDateTime.class, new DateTimeFormatTO("uuuu-MM-dd'T'HH:mm:ss.SSS")),
				new RestServiceParameterTO("user.id", 123, Integer.class),
				new RestServiceParameterTO("user.username", "user123", String.class)
		)));
	}

}