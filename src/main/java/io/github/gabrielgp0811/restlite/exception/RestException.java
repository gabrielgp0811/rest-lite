/**
 * 
 */
package io.github.gabrielgp0811.restlite.exception;

/**
 * The main exception class for the Rest Lite project.
 * 
 * @author gabrielgp0811
 */
public class RestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7357866417339046032L;

	/**
	 * 
	 */
	private int errorCode = -1;

	/**
	 * 
	 */
	public RestException() {

	}

	/**
	 * @param errorCode The error code.
	 */
	public RestException(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @param message the detail message. The detail message is saved forlater
	 *                retrieval by the {@link #getMessage()} method.
	 */
	public RestException(String message) {
		super(message);
	}

	/**
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method). (A null value ispermitted, and
	 *              indicates that the cause is nonexistent orunknown.)
	 */
	public RestException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param errorCode The error code.
	 * @param message   the detail message. The detail message is saved forlater
	 *                  retrieval by the {@link #getMessage()} method.
	 */
	public RestException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * @param errorCode The error code.
	 * @param cause     the cause (which is saved for later retrieval by the
	 *                  {@link #getCause()} method). (A null value ispermitted, and
	 *                  indicates that the cause is nonexistent orunknown.)
	 */
	public RestException(int errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	/**
	 * @param message the detail message (which is saved for later retrieval by the
	 *                {@link #getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method). (A <tt>null</tt> value is
	 *                permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 */
	public RestException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param errorCode The error code.
	 * @param message   the detail message (which is saved for later retrieval by
	 *                  the {@link #getMessage()} method).
	 * @param cause     the cause (which is saved for later retrieval by the
	 *                  {@link #getCause()} method). (A <tt>null</tt> value is
	 *                  permitted, and indicates that the cause is nonexistent or
	 *                  unknown.)
	 */
	public RestException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * @param message            the detail message.
	 * @param cause              the cause. (A {@code null} value is permitted, and
	 *                           indicates that the cause is nonexistent or
	 *                           unknown.)
	 * @param enableSuppression  whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public RestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param errorCode          The error code.
	 * @param message            the detail message.
	 * @param cause              the cause. (A {@code null} value is permitted, and
	 *                           indicates that the cause is nonexistent or
	 *                           unknown.)
	 * @param enableSuppression  whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public RestException(int errorCode, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @return The root localized message
	 */
	public String getRootLocalizedMessage() {
		return getRootCause().getLocalizedMessage();
	}

	/**
	 * @return The root cause
	 */
	public Throwable getRootCause() {
		Throwable t = getCause();

		if (t == null) {
			return this;
		}

		while (t.getCause() != null) {
			t = t.getCause();
		}

		return t;
	}

	@Override
	public String toString() {
		String s = getClass().getName() + "(" + errorCode + ")";
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
	}

}