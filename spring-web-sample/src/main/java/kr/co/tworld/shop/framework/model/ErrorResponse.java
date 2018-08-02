package kr.co.tworld.shop.framework.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Error Response class
 * @author Sangjun, Park
 *
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(Include.NON_EMPTY)
public class ErrorResponse {
	
	private String errorField;
	private String errorMessage;
	
	public ErrorResponse(final String errorField) {
		this.errorField = errorField;
	}

}
