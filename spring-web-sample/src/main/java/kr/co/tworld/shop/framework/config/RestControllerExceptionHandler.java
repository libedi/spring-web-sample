package kr.co.tworld.shop.framework.config;

import java.io.FileNotFoundException;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import kr.co.tworld.shop.common.exception.ResourceConflictException;
import kr.co.tworld.shop.framework.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * RestController Exception Handler
 * @author Sang jun, Park
 *
 */
@Slf4j
@Order(1)
@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * 400 (BAD_REQUEST)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.error(ex.getMessage(), ex);
		final FieldError firstFieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().get();
		return super.handleExceptionInternal(ex,
				new ErrorResponse(firstFieldError.getField(), firstFieldError.getDefaultMessage()), headers, status,
				request);
	}
	
	/**
	 * 400 (BAD_REQUEST)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ErrorResponse handleBadRequest(final IllegalArgumentException ex) {
		log.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Bad Request");
	}
	
	/**
	 * 404 (NOT_FOUND)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ ResourceNotFoundException.class, FileNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ErrorResponse handleNotFound(final Exception ex) {
		log.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Not Found");
	}
	
	/**
	 * 409 (CONFLICT)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ResourceConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	protected ErrorResponse handleConflict(final ResourceConflictException ex) {
		log.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Conflict");
	}
	
	/**
	 * 500 (I/O Error)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorResponse handleDataAccessError(final DataAccessException ex) {
		log.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Internal Server Error");
	}
	
	/**
	 * 500 (HTTP Client/Server Error)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ HttpClientErrorException.class, HttpServerErrorException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorResponse handleHttpError(final HttpStatusCodeException ex) {
		log.error("HTTP ERROR STATUS: {} - {}", ex.getStatusCode(), ex.getStatusText());
		log.error("HTTP ERROR BODY: {}", ex.getResponseBodyAsString(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getStatusText()) ? ex.getStatusText() : "Internal Server Error");
	}
	
	/**
	 * 500 (Rest Client Error)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RestClientException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorResponse handleRestClientError(final RestClientException ex) {
		log.error("REST CLIENT ERROR: {}", ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Internal Server Error");
	}
	
	/**
	 * 500 (INTERNAL_SERVER_ERROR)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorResponse handleInternalServerError(final Exception ex) {
		log.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Internal Server Error");
	}
	
}
