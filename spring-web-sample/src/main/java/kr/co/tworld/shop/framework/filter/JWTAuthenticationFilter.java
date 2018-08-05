package kr.co.tworld.shop.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import kr.co.tworld.shop.framework.security.service.TokenAuthenticationService;

/**
 * JWT Authentication Filter
 * @author Sangjun, Park
 *
 */
public class JWTAuthenticationFilter extends GenericFilterBean {
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException {
		Authentication authentication = 
				this.tokenAuthenticationService.getAuthentication((HttpServletRequest) request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

}
