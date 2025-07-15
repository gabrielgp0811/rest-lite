/**
 * 
 */
package io.github.gabrielgp0811.restlite;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.manager.RestManager;
import io.github.gabrielgp0811.restlite.service.RestService;

/**
 * @author gabrielgp0811
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestServiceTest {

	private static final Logger LOGGER = Logger.getLogger(RestServiceTest.class.getName());

	/**
	 * URL acquired from {@link https://restful-api.dev/}.
	 */
	private static final String API_REST_URL = "https://api.restful-api.dev/objects";

	private static RestManager restManager = null;
	private static String id = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		LogManager.getLogManager().readConfiguration();

		restManager = RestManager.getRestManager();
	}

	@Test
	@Order(1)
	public void testListAllObjects() throws RestException {
		RestService restService = restManager.createService();

		restService.setUrl(API_REST_URL);

		restService.setMethod("GET");
		restService.setContentType("application/json");
		restService.setHeader("User-Agent", "REST Lite/1.0.0");

		LOGGER.info("Result:" + restService.getStringResult());
	}

	@Test
	@Order(2)
	public void testListObjectsById() throws RestException {
		RestService restService = restManager.createService();

		restService.setUrl(API_REST_URL);

		restService.setMethod("GET");
		restService.setContentType("application/json");
		restService.setHeader("User-Agent", "REST Lite/1.0.0");
		restService.setParameter("id", new Integer[] { 3, 4 });

		LOGGER.info("Result:" + restService.getStringResult());
	}

	@Test
	@Order(3)
	public void testGetObjectByPathId() throws RestException {
		RestService restService = restManager.createService();

		restService.setUrl(API_REST_URL.concat("/3"));

		restService.setMethod("GET");
		restService.setContentType("application/json");
		restService.setHeader("User-Agent", "REST Lite/1.0.0");

		LOGGER.info("Result:" + restService.getStringResult());
	}

	@Test
	@Order(4)
	public void testGetObjectByPathId_WithParameter() throws RestException {
		RestService restService = restManager.createService();

		restService.setUrl(API_REST_URL.concat("/:id"));

		restService.setMethod("GET");
		restService.setContentType("application/json");
		restService.setHeader("User-Agent", "REST Lite/1.0.0");
		restService.setParameter("id", Integer.valueOf(3));

		LOGGER.info("Result:" + restService.getStringResult());
	}

	@Test
	@Order(5)
	public void testAddObject() throws RestException {
		RestService restService = restManager.createService();

		restService.setUrl(API_REST_URL);

		restService.setMethod("POST");
		restService.setContentType("application/json");
		restService.setHeader("User-Agent", "REST Lite/1.0.0");
		restService.setParameter("name", "Apple MacBook Pro 16");
		restService.setParameter("data.year", 2019);
		restService.setParameter("data.price", 1849.99);
		restService.setParameter("data.CPU Model", "Intel Core i9");
		restService.setParameter("data.Hard disk size", "1 TB");

		String result = restService.getStringResult();

		LOGGER.info("Result:" + result);

		int index = result.indexOf("\"id\":\"");

		id = result.substring(index + "\"id\":\"".length(), result.indexOf("\"", index + "\"id\":\"".length()));
	}

	@Test
	@Order(6)
	public void testUpdateObjectById() throws RestException {
		RestService restService = restManager.createService();

		restService.setUrl(API_REST_URL.concat("/:id"));

		restService.setMethod("PUT");
		restService.setContentType("application/json");
		restService.setHeader("User-Agent", "REST Lite/1.0.0");
		restService.setParameter("id", id);
		restService.setParameter("name", "Apple MacBook Pro 16");
		restService.setParameter("data.year", 2019);
		restService.setParameter("data.price", 1849.99);
		restService.setParameter("data.CPU Model", "Intel Core i9");
		restService.setParameter("data.Hard disk size", "1 TB");
		restService.setParameter("data.color", "silver");
		
		LOGGER.info("Result:" + restService.getStringResult());
	}
	
	@Test
	@Order(7)
	public void testDeleteObjectById() throws RestException {
		RestService restService = restManager.createService();
		
		restService.setUrl(API_REST_URL.concat("/:id"));
		
		restService.setMethod("DELETE");
		restService.setContentType("application/json");
		restService.setHeader("User-Agent", "REST Lite/1.0.0");
		restService.setParameter("id", id);
		
		LOGGER.info("Result:" + restService.getStringResult());
	}
	
}