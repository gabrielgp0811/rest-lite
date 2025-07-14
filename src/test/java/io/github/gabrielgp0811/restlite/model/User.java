/**
 * 
 */
package io.github.gabrielgp0811.restlite.model;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import java.io.Serializable;
import java.util.Date;

import io.github.gabrielgp0811.jsonlite.annotation.JsonPattern;
import io.github.gabrielgp0811.restlite.annotation.RequestHeader;
import io.github.gabrielgp0811.restlite.annotation.RestService;
import io.github.gabrielgp0811.restlite.annotation.RestServiceParameter;
import io.github.gabrielgp0811.restlite.annotation.RestServices;

/**
 * @author gabrielgp0811
 */
@RestServices(
	protocol = "http",
	host = "localhost",
	port = 8080,
	path = "users",
	method = "GET",
	headers = {
		@RequestHeader(name = "Accept", value = "application/json"),
	},
	value = {
		@RestService(name = "User.findAll"),
		@RestService(
			name = "User.findById",
			contentType = "application/x-www-form-urlencoded",
			parameters = {
				@RestServiceParameter(name = "id", type = Integer.class)
			}
		),
		@RestService(
			name = "User.findByPathId",
			path = ":id",
			contentType = "application/x-www-form-urlencoded"
		),
		@RestService(
			name = "User.findByUsername",
			contentType = "application/x-www-form-urlencoded",
			parameters = {
				@RestServiceParameter(name = "username", type = String.class)
			}
		),
		@RestService(
			name = "User.postAsFormUrlEncoded",
			method = "POST",
			contentType = "application/x-www-form-urlencoded",
			parameters = {
				@RestServiceParameter(name = "username", type = String.class),
				@RestServiceParameter(name = "password", type = String.class),
				@RestServiceParameter(
					name = "birthDate",
					type = Date.class,
					dateTimeFormat = @JsonPattern("yyyy-MM-dd")
				),
			},
			expectedStatusCodes = { HTTP_OK, HTTP_CREATED }
		),
		@RestService(
			name = "User.postAsJson",
			method = "POST",
			contentType = "application/json",
			parameters = {
				@RestServiceParameter(name = "user", type = User.class)
			},
			expectedStatusCodes = { HTTP_OK, HTTP_CREATED }
		),
		@RestService(
			name = "User.put",
			method = "PUT",
			contentType = "application/json",
			parameters = {
				@RestServiceParameter(name = "user", type = User.class)
			}
		),
		@RestService(
			name = "User.putPathId",
			path = ":id",
			method = "PUT",
			contentType = "application/json",
			parameters = {
				@RestServiceParameter(name = "user", type = User.class)
			}
		),
		@RestService(
			name = "User.delete",
			method = "DELETE",
			contentType = "application/json",
			parameters = {
				@RestServiceParameter(name = "user", type = User.class)
			}
		),
		@RestService(
			name = "User.deleteById",
			method = "DELETE",
			contentType = "application/json",
			parameters = {
				@RestServiceParameter(name = "id", type = Integer.class)
			}
		),
		@RestService(
			name = "User.deleteByUsername",
			method = "DELETE",
			contentType = "application/json",
			parameters = {
				@RestServiceParameter(name = "username", type = String.class)
			}
		)
	}
)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1684957779220800997L;

	private Integer id = null;

	private String username = null;

	private String password = null;

	@JsonPattern("yyyy-MM-dd")
	private Date birthDate = null;

	public User() {
	}

	public User(Integer id, String username, String password, Date birthDate) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", birthDate=" + birthDate
				+ "]";
	}

}