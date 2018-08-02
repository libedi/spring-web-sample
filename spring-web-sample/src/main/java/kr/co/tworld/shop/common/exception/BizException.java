package kr.co.tworld.shop.common.exception;

/**
 * Tworld Business Exception class
 * @author Sang jun, Park
 *
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -7936097380995388825L;

	public BizException() {}

	public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

}
