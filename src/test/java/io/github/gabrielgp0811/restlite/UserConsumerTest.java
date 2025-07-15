/**
 * 
 */
package io.github.gabrielgp0811.restlite;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.gabrielgp0811.restlite.consumer.UserConsumer;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.model.User;

/**
 * @author gabrielgp0811
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserConsumerTest {

	private static final Logger LOGGER = Logger.getLogger(UserConsumerTest.class.getName());

	private static Process process = null;
	private static UserConsumer consumer = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		LogManager.getLogManager().readConfiguration();

		ProcessBuilder pb = new ProcessBuilder("java", "-jar", "rest-server-1.0.0.jar");

		String pathname = Thread.currentThread().getContextClassLoader().getResource("").getFile();

		pb.directory(new File(pathname));

		process = pb.start();

		try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			int index = 0;

			while ((line = input.readLine()) != null) {
				System.out.println(line);
				index++;

				if (index == 18) // Total number of lines when launching rest-server JAR
					break;
			}
		}

		consumer = new UserConsumer();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		process.destroy();
	}

	@Test
	@Order(1)
	public void testUserFindAll() throws RestException {
		consumer.findAll().stream().map(user -> user.toString()).forEach(LOGGER::info);
	}

	@Test
	@Order(2)
	public void testUserGetById() throws RestException {
		LOGGER.info("(id=1) -> " + consumer.findById(1));
		LOGGER.info("(id=2) -> " + consumer.findById(2));
		LOGGER.info("(id=3) -> " + consumer.findById(3));
	}

	@Test
	@Order(3)
	public void testExceptionOnUserGetById() throws RestException {
		assertThrows(RestException.class, () -> consumer.findById(4), "Expected exception not thrown.");
	}

	@Test
	@Order(4)
	public void testUserGetByPathId() throws RestException {
		LOGGER.info("(id=1) -> " + consumer.findByPathId(1));
		LOGGER.info("(id=2) -> " + consumer.findByPathId(2));
		LOGGER.info("(id=3) -> " + consumer.findByPathId(3));
	}

	@Test
	@Order(5)
	public void testExceptionOnUserGetByPathId() throws RestException {
		assertThrows(RestException.class, () -> consumer.findByPathId(4), "Expected exception not thrown.");
	}

	@Test
	@Order(6)
	public void testUserGetByUsername() throws RestException {
		LOGGER.info("(username=user1) -> " + consumer.findByUsername("user1"));
		LOGGER.info("(username=user2) -> " + consumer.findByUsername("user2"));
		LOGGER.info("(username=user3) -> " + consumer.findByUsername("user3"));
	}

	@Test
	@Order(7)
	public void testExceptionOnUserGetByUsername() throws RestException {
		assertThrows(RestException.class, () -> consumer.findByUsername("user4"), "Expected exception not thrown.");
	}

	@Test
	@Order(8)
	public void testUserPostAsFormUrlEncoded() throws RestException {
		User user4 = new User();

		user4.setUsername("user4");
		user4.setPassword("password4");
		user4.setBirthDate(Date.from(LocalDate.of(1962, 10, 16).atStartOfDay(ZoneId.systemDefault()).toInstant()));

		LOGGER.info("postAsFormUrlEncoded " + user4 + " -> " + consumer.postAsFormUrlEncoded(user4));
	}

	@Test
	@Order(9)
	public void testUserPostAsJson() throws RestException {
		User user5 = new User();

		user5.setUsername("user5");
		user5.setPassword("password5");
		user5.setBirthDate(Date.from(LocalDate.of(1956, 8, 17).atStartOfDay(ZoneId.systemDefault()).toInstant()));

		LOGGER.info("postAsJson " + user5 + " -> " + consumer.postAsJson(user5));
	}

	@Test
	@Order(10)
	public void testUserPut() throws RestException {
		User user6 = new User();

		user6.setId(4);
		user6.setUsername("user6");
		user6.setPassword("password6");
		user6.setBirthDate(Date.from(LocalDate.of(1956, 8, 17).atStartOfDay(ZoneId.systemDefault()).toInstant()));

		LOGGER.info(consumer.put(user6));
	}

	@Test
	@Order(11)
	public void testUserPutPathId() throws RestException {
		User user7 = new User();

		user7.setUsername("user7");
		user7.setPassword("password7");
		user7.setBirthDate(Date.from(LocalDate.of(1985, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

		LOGGER.info(consumer.putPathId(5, user7));
	}

	@Test
	@Order(12)
	public void testUserDelete() throws RestException {
		LOGGER.info("deleteById (id=4) -> " + consumer.deleteById(4));
		LOGGER.info("deleteByUsername (username=user7) -> " + consumer.deleteByUsername("user7"));
	}

}