package kr.co.tworld.shop.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.tworld.shop.framework.model.ErrorResponse;
import kr.co.tworld.shop.framework.security.service.TokenAuthenticationService;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT Authentication Filter
 * @author Sangjun, Park
 *
 */
@Slf4j
public class JWTAuthenticationFilter extends GenericFilterBean {
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException {
		try {
			Authentication authentication = 
					this.tokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().print(this.objectMapper.writeValueAsString(new ErrorResponse(null, e.getMessage())));
			response.flushBuffer();
		}
	}

}
