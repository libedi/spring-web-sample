package kr.co.tworld.shop.common.exception;

/**
 * ResourceConflictException
 * @author Sang jun, Park
 *
 */
public class ResourceConflictException extends RuntimeException {

	private static final long serialVersionUID = -3368889514327248934L;

	public ResourceConflictException() {}

	public ResourceConflictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ResourceConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceConflictException(String message) {
		super(message);
	}

	public ResourceConflictException(Throwable cause) {
		super(cause);
	}

}
