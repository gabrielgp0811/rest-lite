/**
 * 
 */
package io.github.gabrielgp0811.restlite;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
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
import io.github.gabrielgp0811.restlite.model.User;
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
				new RestServiceParameterTO("test", "test123"),
				new RestServiceParameterTO("number", 123),
				new RestServiceParameterTO("conditionTrue", true),
				new RestServiceParameterTO("conditionFalse", false),
				new RestServiceParameterTO("stringArray", new String[] { "first", "second", "third" }),
				new RestServiceParameterTO("dateArray1", new LocalDate[] { LocalDate.now(), LocalDate.of(1990, 1, 20) }),
				new RestServiceParameterTO("dateArray2", new LocalDate[] { LocalDate.now(), LocalDate.of(1990, 1, 20) }, new DateTimeFormatTO("MM/dd/yyyy")),
				new RestServiceParameterTO("date1", new Date()),
				new RestServiceParameterTO("date2", new Date(), new DateTimeFormatTO("yyyy-MM-dd")),
				new RestServiceParameterTO("localDate1", LocalDate.now()),
				new RestServiceParameterTO("localDate2", LocalDate.now(), new DateTimeFormatTO("MM/dd")),
				new RestServiceParameterTO("localTime1", LocalTime.now()),
				new RestServiceParameterTO("localTime2", LocalTime.now(), new DateTimeFormatTO("hh:mm a")),
				new RestServiceParameterTO("localDateTime1", LocalDateTime.now()),
				new RestServiceParameterTO("localDateTime2", LocalDateTime.now(), new DateTimeFormatTO("MM/dd/yyyy hh:mm:ss.SSS a")),
				new RestServiceParameterTO("localDateTime2", LocalDateTime.now(), new DateTimeFormatTO("yyyy-MM-dd'T'HH:mm:ss.SSS"))
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
				new RestServiceParameterTO("test", "test123"),
				new RestServiceParameterTO("number", 123),
				new RestServiceParameterTO("conditionTrue", true),
				new RestServiceParameterTO("conditionFalse", false),
				new RestServiceParameterTO("stringArray", new String[] { "first", "second", "third" }),
				new RestServiceParameterTO("dateArray1", new LocalDate[] { LocalDate.now(), LocalDate.of(1990, 1, 20) }),
				new RestServiceParameterTO("dateArray2", new LocalDate[] { LocalDate.now(), LocalDate.of(1990, 1, 20) }, new DateTimeFormatTO("MM/dd/yyyy")),
				new RestServiceParameterTO("date1", new Date()),
				new RestServiceParameterTO("date2", new Date(), new DateTimeFormatTO("yyyy-MM-dd")),
				new RestServiceParameterTO("localDate1", LocalDate.now()),
				new RestServiceParameterTO("localDate2", LocalDate.now(), new DateTimeFormatTO("MM/dd")),
				new RestServiceParameterTO("localTime1", LocalTime.now()),
				new RestServiceParameterTO("localTime2", LocalTime.now(), new DateTimeFormatTO("hh:mm a")),
				new RestServiceParameterTO("localDateTime1", LocalDateTime.now()),
				new RestServiceParameterTO("localDateTime2", LocalDateTime.now(), new DateTimeFormatTO("MM/dd/uuuu hh:mm:ss.SSS a")),
				new RestServiceParameterTO("localDateTime2", LocalDateTime.now(), new DateTimeFormatTO("uuuu-MM-dd'T'HH:mm:ss.SSS")),
				new RestServiceParameterTO("user", new User(1, "gabrielgp0811", "password123", Date.from(LocalDate.of(1987, 11, 8).atStartOfDay(ZoneId.systemDefault()).toInstant())))
		)));
	}

}