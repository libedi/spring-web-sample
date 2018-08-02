package kr.co.tworld.shop.common.exception;

/**
 * Tworld Business Exception class
 * @author Sang jun, Park
 *
 */
public class TwddBizException extends RuntimeException {

	private static final long serialVersionUID = -7936097380995388825L;

	public TwddBizException() {}

	public TwddBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TwddBizException(String message, Throwable cause) {
		super(message, cause);
	}

	public TwddBizException(String message) {
		super(message);
	}

	public TwddBizException(Throwable cause) {
		super(cause);
	}

}
