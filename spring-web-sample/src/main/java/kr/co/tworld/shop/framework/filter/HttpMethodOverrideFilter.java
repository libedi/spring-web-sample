package kr.co.tworld.shop.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * HttpMethodOverrideFilter class
 * @author Sang jun, Park
 *
 */
public class HttpMethodOverrideFilter extends OncePerRequestFilter {
	
	private static final String HTTP_METHOD_OVERRIDE_HEADER_NAME = "X-HTTP-Method-Override";

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		
		final String headerValue = request.getHeader(HTTP_METHOD_OVERRIDE_HEADER_NAME);
		if(StringUtils.isNotEmpty(headerValue) && StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			filterChain.doFilter(new HttpMethodRequestWrapper(request, headerValue), response);
		} else {
			filterChain.doFilter(request, response);
		}
	}
	
	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {
		
		private final String method;
		
		public HttpMethodRequestWrapper(final HttpServletRequest request, final String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}

}
