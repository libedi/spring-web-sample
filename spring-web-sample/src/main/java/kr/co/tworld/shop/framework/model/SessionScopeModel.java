package kr.co.tworld.shop.framework.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SessionScope
@Component
@Getter @Setter
@ToString
public class SessionScopeModel {

	private String username;
	private String password;
	private String role;
}
