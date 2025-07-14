/**
 * 
 */
package io.github.gabrielgp0811.restlite.consumer;

import java.util.List;

import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.model.User;
import io.github.gabrielgp0811.restlite.service.RestService;

/**
 * @author gabrielgp0811
 */
public class UserConsumer extends ServiceConsumer<User> {

	/**
	 * 
	 */
	public UserConsumer() {
		super("io.github.gabrielgp0811.restlite.model");
	}

	public List<User> findAll() throws RestException {
		return getTypedResultList("User.findAll");
	}

	public User findById(int id) throws RestException {
		return getTypedSingleResult("User.findById", "id", id);
	}

	public User findByPathId(int id) throws RestException {
		return getTypedSingleResult("User.findByPathId", "id", id);
	}

	public User findByUsername(String username) throws RestException {
		return getTypedSingleResult("User.findByUsername", "username", username);
	}

	public String postAsFormUrlEncoded(User user) throws RestException {
		RestService service = manager.createService("User.postAsFormUrlEncoded");

		service.setParameter("username", user.getUsername());
		service.setParameter("password", user.getPassword());
		service.setParameter("birthDate", user.getBirthDate());

		return service.getStringResult();
	}

	public String postAsJson(User user) throws RestException {
		RestService service = manager.createService("User.postAsJson");

		service.setParameter("user", user);

		return service.getStringResult();
	}

	public String put(User user) throws RestException {
		RestService service = manager.createService("User.put");

		service.setParameter("user", user);

		return service.getStringResult();
	}

	public String putPathId(Integer id, User user) throws RestException {
		RestService service = manager.createService("User.putPathId");

		service.setParameter("id", id);
		service.setParameter("user", user);

		return service.getStringResult();
	}

	public String delete(User user) throws RestException {
		RestService service = manager.createService("User.delete");

		service.setParameter("user", user);

		return service.getStringResult();
	}

	public String deleteById(int id) throws RestException {
		RestService service = manager.createService("User.deleteById");

		service.setParameter("id", id);

		return service.getStringResult();
	}

	public String deleteByUsername(String username) throws RestException {
		RestService service = manager.createService("User.deleteByUsername");

		service.setParameter("username", username);

		return service.getStringResult();
	}

}