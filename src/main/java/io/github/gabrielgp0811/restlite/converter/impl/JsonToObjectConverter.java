/**
 * 
 */
package io.github.gabrielgp0811.restlite.converter.impl;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.gabrielgp0811.restlite.converter.Converter;
import io.github.gabrielgp0811.restlite.exception.RestException;

/**
 * Class responsible for deserializing JSON strings into Java objects.
 * 
 * @author gabrielgp0811
 */
public class JsonToObjectConverter implements Converter<String, Object> {

	enum JsonToken {

		NONE, CURLY_OPEN, CURLY_CLOSE, SQUARED_OPEN, SQUARED_CLOSE, COLON, COMMA, STRING, NUMBER, TRUE, FALSE, NULL

	}

	private final String WORD_BREAK = "{}[],:\"";

	private PushbackReader reader = null;

	/**
	 * 
	 */
	public JsonToObjectConverter() {

	}

	@Override
	public Object convert(String input) throws RestException {
		if (input == null || input.trim().isEmpty()) {
			return null;
		}

		input = input.trim();

		reader = new PushbackReader(new StringReader(input), input.length());

		try {
			Object value = parseValue();

			if (value == null) {
				throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR, "Error converting input.");
			}

			return value;
		} catch (IOException e) {
			throw new RestException(HttpURLConnection.HTTP_INTERNAL_ERROR, "Error converting input.", e);
		}
	}

	/**
	 * Parse value.
	 * 
	 * @return The value parsed.
	 * @throws IOException Error on parse.
	 */
	private Object parseValue() throws IOException {
		return parseByToken(nextToken());
	}

	/**
	 * Retrieving next token.
	 * 
	 * @return The next token.
	 * @throws IOException Error retrieving next token.
	 */
	private JsonToken nextToken() throws IOException {
		ignoreWhitespace();

		if (peek() == -1) {
			return JsonToken.NONE;
		}

		char ch = peekChar();

		switch (ch) {
		case '{':
			return JsonToken.CURLY_OPEN;
		case '}':
			reader.read();
			return JsonToken.CURLY_CLOSE;
		case '[':
			return JsonToken.SQUARED_OPEN;
		case ']':
			reader.read();
			return JsonToken.SQUARED_CLOSE;
		case ',':
			reader.read();
			return JsonToken.COMMA;
		case '"':
			return JsonToken.STRING;
		case ':':
			return JsonToken.COLON;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case '-':
			return JsonToken.NUMBER;
		default:
			break;
		}

		String nextWord = nextWord();

		switch (nextWord) {
		case "true":
			return JsonToken.TRUE;
		case "false":
			return JsonToken.FALSE;
		case "null":
		case "undefined":
			return JsonToken.NULL;
		default:
			break;
		}

		return JsonToken.NONE;
	}

	/**
	 * Parsing by token.
	 * 
	 * @param token The token.
	 * @throws IOException Error on parse.
	 */
	private Object parseByToken(JsonToken token) throws IOException {
		switch (token) {
		case STRING:
			return parseString();
		case NUMBER:
			return parseNumber();
		case CURLY_OPEN:
			return parseObject();
		case SQUARED_OPEN:
			return parseArray();
		case TRUE:
			return true;
		case FALSE:
			return false;
		default:
			return null;
		}
	}

	/**
	 * Parse String.
	 * 
	 * @return The String.
	 * @throws IOException Error on parse.
	 */
	private String parseString() throws IOException {
		StringBuilder builder = new StringBuilder();

		// Ignore double quote
		reader.read();

		boolean parsing = true;
		while (parsing) {
			if (peek() == -1) {
				break;
			}

			char ch = (char) reader.read();

			switch (ch) {
			case '"':
				parsing = false;
				break;
			case '\\':
				if (peek() == -1) {
					parsing = false;
					break;
				}

				ch = (char) reader.read();

				switch (ch) {
				case '"':
				case '\\':
				case '/':
					builder.append(ch);
					break;
				case 'b':
					builder.append('\b');
					break;
				case 'r':
					builder.append('\r');
					break;
				case 'n':
					builder.append('\n');
					break;
				case 'f':
					builder.append('\f');
					break;
				case 't':
					builder.append('\t');
					break;
				case 'u':
					char[] hex = new char[4];
					reader.read(hex);

					builder.append((char) Integer.parseInt(String.valueOf(hex), 16));
					break;
				default:
					break;
				}

				break;
			default:
				builder.append(ch);
				break;
			}
		}

		return builder.toString();
	}

	/**
	 * Parse number.
	 * 
	 * @return The number.
	 * @throws IOException Error on parse.
	 */
	private Number parseNumber() throws IOException {
		String number = nextWord();

		if (number.indexOf('.') == -1) {
			try {
				return Byte.valueOf(number);
			} catch (NumberFormatException e) {
			}

			try {
				return Short.valueOf(number);
			} catch (NumberFormatException e) {
			}

			try {
				return Integer.valueOf(number);
			} catch (NumberFormatException e) {
			}

			try {
				return Long.valueOf(number);
			} catch (NumberFormatException e) {
			}
		}

		try {
			return Float.valueOf(number);
		} catch (NumberFormatException e) {
		}

		try {
			return Double.valueOf(number);
		} catch (NumberFormatException e) {
		}

		throw new IOException("Error parsing number " + number);
	}

	/**
	 * Parse an object into a {@link java.util.Map}.
	 * 
	 * @return The {@link java.util.Map} object.
	 * @throws IOException Error on parse.
	 */
	private Map<String, Object> parseObject() throws IOException {
		Map<String, Object> map = new HashMap<>();

		// Ignores '{'
		reader.read();

		while (true) {
			JsonToken token = nextToken();

			switch (token) {
			case NULL:
				return null;
			case COMMA:
				continue;
			case CURLY_CLOSE:
				return map;
			default:
				String name = parseString();
				if (name == null) {
					return null;
				}

				if (nextToken() != JsonToken.COLON) {
					return null;
				}

				// Ignores ':'
				reader.read();

				map.put(name, parseByToken(nextToken()));

				break;
			}
		}
	}

	/**
	 * Parse array.
	 * 
	 * @return The {@link java.util.List} object.
	 * @throws IOException Error on parse.
	 */
	private List<Object> parseArray() throws IOException {
		List<Object> array = new ArrayList<>();

		// Ignores '['
		reader.read();

		boolean parsing = true;
		while (parsing) {
			JsonToken token = nextToken();

			switch (token) {
			case COMMA:
				continue;
			case NONE:
			case SQUARED_CLOSE:
				parsing = false;
				break;
			default:
				Object value = parseByToken(token);

				array.add(value);
				break;
			}
		}

		return array;
	}

	/**
	 * Parse next word.
	 * 
	 * @return The word.
	 * @throws IOException Error on parse.
	 */
	private String nextWord() throws IOException {
		StringBuilder builder = new StringBuilder();

		while (!isWordBreak(peekChar())) {
			builder.append((char) reader.read());

			if (peek() == -1)
				break;
		}

		return builder.toString();
	}

	/**
	 * Peek next character.
	 * 
	 * @return The next character.
	 * @throws IOException Error peeking.
	 */
	private char peekChar() throws IOException {
		return (char) peek();
	}

	/**
	 * Peek next character.
	 * 
	 * @return The next character as an <code>int</code>.
	 * @throws IOException Error peeking.
	 */
	private int peek() throws IOException {
		int c = reader.read();

		reader.unread(c);

		return c;
	}

	/**
	 * Ignores whitespace.
	 * 
	 * @throws IOException Error ignoring whitespace.
	 */
	private void ignoreWhitespace() throws IOException {
		while (Character.isWhitespace(peek())) {
			reader.read();

			if (peek() == -1)
				break;
		}
	}

	/**
	 * Checks if character is word break.
	 * 
	 * @param c The character.
	 * @return <strong><span style="color:#7f0055">true</span></strong> if is word
	 *         break; <strong><span style="color:#7f0055">false</span></strong>
	 *         otherwise.
	 */
	private boolean isWordBreak(char c) {
		return Character.isWhitespace(c) || WORD_BREAK.indexOf(c) != -1;
	}

}