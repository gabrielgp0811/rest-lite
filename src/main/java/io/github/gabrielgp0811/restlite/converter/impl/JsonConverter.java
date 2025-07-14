/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import io.github.gabrielgp0811.jsonlite.Json;
import io.github.gabrielgp0811.jsonlite.JsonEntry;
import io.github.gabrielgp0811.jsonlite.impl.JsonObject;
import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.to.DateTimeFormatTO;
import io.github.gabrielgp0811.restlite.to.RestServiceParameterTO;

/**
 * Main converter for parameters for Content-Type
 * <strong>application/json</strong>.
 * 
 * @author gabrielgp0811
 */
public class JsonConverter implements Converter<Collection<RestServiceParameterTO>, String> {

	/**
	 * 
	 */
	public JsonConverter() {

	}

	@Override
	public String convert(Collection<RestServiceParameterTO> input) throws RestException {
		if (input == null) {
			input = new ArrayList<>();
		}

		RestServiceParameterTO parameter = input.stream()
				.filter(Objects::nonNull)
				.filter(p -> !p.getName().trim().isEmpty())
				.filter(p -> p.getValue() == null && !p.isOptional())
				.findFirst()
				.orElseGet(RestServiceParameterTO::new);

		if (!parameter.getName().trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Must inform parameter '" + parameter.getName() + "'.");
		}

		parameter = input.stream()
				.filter(Objects::nonNull)
				.filter(p -> !p.getName().trim().isEmpty())
				.filter(p -> p.getValue() != null)
				.filter(p -> !p.getType().getName().equals(p.getValue().getClass().getName()))
				.findFirst()
				.orElseGet(RestServiceParameterTO::new);

		if (!parameter.getName().trim().isEmpty()) {
			throw new RestException(HttpURLConnection.HTTP_BAD_REQUEST,
					"Parameter's type '" + parameter.getName() + "' informed is '"
							+ parameter.getValue().getClass().getName() + "'. Expected is '"
							+ parameter.getType().getName() + "'.");
		}

		if (input.size() == 1) {
			return input.stream()
					.filter(Objects::nonNull)
					.filter(p -> !p.getName().trim().isEmpty())
					.map(this::toJson)
					.filter(Objects::nonNull)
					.map(json -> json.toString())
					.findFirst()
					.get();
		}

		JsonObject json = new JsonObject();

		input.stream()
			.filter(Objects::nonNull)
			.filter(p -> !p.getName().trim().isEmpty())
			.map(this::toJson)
			.forEach(json::addChild);

		return json.toString();
	}

	/**
	 * Converts <code>parameter</code> into a JSON object.
	 * 
	 * @param parameter The parameter.
	 * @return The converted JSON object.
	 */
	private JsonEntry<?> toJson(RestServiceParameterTO parameter) {
		return toJson(new ArrayList<>(Arrays.asList(parameter.getName().split("[.]"))), parameter.getValue(),
				parameter.getDateTimeFormat());
	}

	/**
	 * Converts <code>obj</code> into a JSON object of name contained in recursive
	 * attribute's names using <code>pattern</code>, <code>locale</code> and
	 * <code>timezone</code>.
	 * Take code below as an example:
	 * 
	 * <pre>
	 * String json = new JsonConverter().convert(Arrays.asList(
	 * 		new RestServiceParameterTO("user.id", 123, Integer.class),
	 * 		new RestServiceParameterTO("user.username", "user123", String.class)
	 * 	)
	 * );
	 * </pre>
	 * 
	 * The value of <code>json</code> will be:
	 * 
	 * <pre>
	 * {
	 * 	"user": {
	 * 		"id": 123,
	 * 		"username": "user123"
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param names          The attribute name contained in attribute's names
	 *                       (recursive).
	 * @param obj            The object.
	 * @param dateTimeFormat The date/time format.
	 * @return The converted JSON object.
	 */
	private JsonEntry<?> toJson(List<String> names, Object obj, DateTimeFormatTO dateTimeFormat) {
		if (names.isEmpty()) {
			return null;
		}

		String name = names.remove(0);

		if (!names.isEmpty()) {
			return new JsonObject(name).addChild(toJson(names, obj, dateTimeFormat));
		}

		return Json.toJson(name, obj, dateTimeFormat.getPattern(), dateTimeFormat.getLocale(),
				dateTimeFormat.getTimezone());
	}

}