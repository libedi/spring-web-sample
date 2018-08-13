package kr.co.tworld.shop.sample.model;

import javax.validation.constraints.NotEmpty;

import kr.co.tworld.shop.framework.model.ValidationMarkers.Create;
import kr.co.tworld.shop.framework.model.ValidationMarkers.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Sample entity class
 * @author Sang jun, Park
 *
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString @Builder
public class Sample {
	
	// All groups. Hibernate default message.
	@NotEmpty(groups = Update.class)
	private Integer customerId;
	
	// Specific group. custom message.
	@NotEmpty(groups = {Create.class, Update.class}, message = "${valid.msg.not-empty}")
	private String customerName;
	
	private String company;

}
