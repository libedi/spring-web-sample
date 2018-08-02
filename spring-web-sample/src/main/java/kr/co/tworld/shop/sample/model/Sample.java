package kr.co.tworld.shop.sample.model;

import javax.validation.constraints.NotEmpty;

import kr.co.tworld.shop.framework.model.ValidationMarkers.Create;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Sample entity class
 * @author Sang jun, Park
 *
 */
@Getter @Setter
@ToString
public class Sample {
	
	// All groups. Hibernate default message.
	@NotEmpty
	private String customerId;
	
	// Specific group. custom message.
	@NotEmpty(groups = Create.class, message = "${valid.msg.not-empty}")
	private String customerName;
	
	private String company;

}
