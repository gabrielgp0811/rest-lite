/**
 * 
 */
package io.github.gabrielgp0811.restlite.to;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author gabrielgp0811
 */
public class RestServiceTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5725927936729917340L;

	private String name = null;

	private String url = null;

	private String protocol = null;

	private String host = null;

	private int port = 0;

	private String app = null;

	private String path = null;

	private String method = null;

	private String contentType = null;

	private String charsetRead = null;

	private boolean writable = false;

	private String charsetWrite = null;

	private int connectTimeout = 0;

	private int readTimeout = 0;

	private RequestHeaderTO[] headers = null;

	private RestServiceParameterTO[] parameters = null;

	private int[] expectedStatusCodes = null;

	/**
	 * 
	 */
	public RestServiceTO() {

	}

	/**
	 * @param name the name
	 */
	public RestServiceTO(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		if (name == null) {
			name = "";
		}
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		if (url == null) {
			url = "";
		}
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		if (protocol == null) {
			protocol = "";
		}
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		if (host == null) {
			host = "";
		}
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the app
	 */
	public String getApp() {
		if (app == null) {
			app = "";
		}
		return app;
	}

	/**
	 * @param app the app to set
	 */
	public void setApp(String app) {
		this.app = app;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		if (path == null) {
			path = "";
		}
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		if (method == null) {
			method = "";
		}
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		if (contentType == null) {
			contentType = "";
		}
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the charsetRead
	 */
	public String getCharsetRead() {
		if (charsetRead == null) {
			charsetRead = "";
		}
		return charsetRead;
	}

	/**
	 * @param charsetRead the charsetRead to set
	 */
	public void setCharsetRead(String charsetRead) {
		this.charsetRead = charsetRead;
	}

	/**
	 * @return the writable
	 */
	public boolean isWritable() {
		return writable;
	}

	/**
	 * @param writable the writable to set
	 */
	public void setWritable(boolean writable) {
		this.writable = writable;
	}

	/**
	 * @return the charsetWrite
	 */
	public String getCharsetWrite() {
		if (charsetWrite == null) {
			charsetWrite = "";
		}
		return charsetWrite;
	}

	/**
	 * @param charsetWrite the charsetWrite to set
	 */
	public void setCharsetWrite(String charsetWrite) {
		this.charsetWrite = charsetWrite;
	}

	/**
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the readTimeout
	 */
	public int getReadTimeout() {
		return readTimeout;
	}

	/**
	 * @param readTimeout the readTimeout to set
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	/**
	 * @return the properties
	 */
	public RequestHeaderTO[] getHeaders() {
		if (headers == null) {
			headers = new RequestHeaderTO[0];
		}
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(RequestHeaderTO[] headers) {
		this.headers = headers;
	}

	/**
	 * @return the parameters
	 */
	public RestServiceParameterTO[] getParameters() {
		if (parameters == null) {
			parameters = new RestServiceParameterTO[0];
		}
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(RestServiceParameterTO[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the expectedStatusCodes
	 */
	public int[] getExpectedStatusCodes() {
		if (expectedStatusCodes == null) {
			expectedStatusCodes = new int[0];
		}
		return expectedStatusCodes;
	}

	/**
	 * @param expectedStatusCodes the expectedStatusCodes to set
	 */
	public void setExpectedStatusCodes(int[] expectedStatusCodes) {
		this.expectedStatusCodes = expectedStatusCodes;
	}

	@Override
	public String toString() {
		return "RestServiceTO [name=" + name + ", url=" + url + ", protocol=" + protocol + ", host=" + host + ", port="
				+ port + ", app=" + app + ", path=" + path + ", method=" + method + ", contentType=" + contentType
				+ ", charsetRead=" + charsetRead + ", writable=" + writable + ", charsetWrite=" + charsetWrite
				+ ", connectTimeout=" + connectTimeout + ", readTimeout=" + readTimeout + ", properties="
				+ Arrays.toString(headers) + ", parameters=" + Arrays.toString(parameters) + ", expectedStatusCodes="
				+ Arrays.toString(expectedStatusCodes) + "]";
	}

}