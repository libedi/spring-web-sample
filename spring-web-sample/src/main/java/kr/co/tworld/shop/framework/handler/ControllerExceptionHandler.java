package kr.co.tworld.shop.framework.handler;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller Exception Handler
 * @author Sang jun, Park
 *
 */
@Slf4j
@Order(2)
@ControllerAdvice(annotations = Controller.class)
public class ControllerExceptionHandler {

	/**
	 * Database Exception
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(DataAccessException.class)
	protected String handleDataAccessError(final DataAccessException ex) {
		log.error("DATABASE ERROR: {}", ex.getMessage(), ex);
		return "redirect:/error/500";
	}
	
	/**
	 * Exception
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	protected String handleServerError(final Exception ex) {
		log.error("SERVER ERROR: {}", ex.getMessage(), ex);
		return "redirect:/error/500";
	}
}
