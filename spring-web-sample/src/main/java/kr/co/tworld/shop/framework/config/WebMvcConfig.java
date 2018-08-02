package kr.co.tworld.shop.framework.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.tworld.shop.framework.filter.HttpMethodOverrideFilter;
import kr.co.tworld.shop.framework.model.ErrorResponse;

/**
 * Web MVC Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * Validator Configuration
	 * @param messageSource
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(final MessageSource messageSource) {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}
	
	/**
	 * Default Error Attribute
	 * @return
	 */
	@Bean
	public ErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes() {

			@Override
			public Map<String, Object> getErrorAttributes(final WebRequest webRequest,
					final boolean includeStackTrace) {
				Integer status = (Integer) webRequest.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE,
						RequestAttributes.SCOPE_REQUEST);
				return objectMapper.convertValue(
						new ErrorResponse(status == null ? "None" : HttpStatus.valueOf(status).getReasonPhrase()),
						new TypeReference<Map<String, Object>>(){});
			}
			
		};
	}
	
	/**
	 * HttpMethod Override Filter
	 * @return
	 */
	@Bean
	public HttpMethodOverrideFilter httpMethodOverrideFilter() {
		return new HttpMethodOverrideFilter();
	}
	
	/**
	 * Filter Registration
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<HttpMethodOverrideFilter> filterRegistrationBean() {
		FilterRegistrationBean<HttpMethodOverrideFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(httpMethodOverrideFilter());
		return filter;
	}
	
	/**
	 * Embeded Server Error page
	 * @return
	 */
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory webServerFactory = new TomcatServletWebServerFactory();
		webServerFactory.addErrorPages(
				new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"),
				new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401"),
				new ErrorPage(HttpStatus.FORBIDDEN, "/error/403"),
				new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"),
				new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
		return webServerFactory;
	}
}
