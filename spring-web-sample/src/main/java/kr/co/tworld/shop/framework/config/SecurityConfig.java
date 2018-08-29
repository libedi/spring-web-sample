package kr.co.tworld.shop.framework.config;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.co.tworld.shop.framework.filter.JWTAuthenticationFilter;
import kr.co.tworld.shop.framework.filter.JWTLoginFilter;
import kr.co.tworld.shop.framework.security.service.SampleUserService;

/**
 * Security Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
		)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public JWTLoginFilter jwtLoginFilter() throws Exception {
		return new JWTLoginFilter(new AntPathRequestMatcher("/api/login"), this.authenticationManager());
	}
	
	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter() {
		return new JWTAuthenticationFilter();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new SampleUserService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling()
				.authenticationEntryPoint((req, resp, e) -> {
					if(StringUtils.contains(req.getHeader(HttpHeaders.ACCEPT), MediaType.APPLICATION_JSON_VALUE)) {
						resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						resp.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
						resp.flushBuffer();
					}
					else {
						resp.sendRedirect("/error/403");
					}
				})
				.accessDeniedHandler((req, resp, e) -> {
					resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
					resp.flushBuffer();
				})
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/login").permitAll()
				.antMatchers(HttpMethod.GET, "/view/login").permitAll()
				.antMatchers(HttpMethod.GET, "/view/sample/excel").permitAll()
				.antMatchers(HttpMethod.GET, "/error/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.addFilterBefore(this.jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(this.jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
		;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService())
			.passwordEncoder(this.passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/webjars/**")
			.antMatchers("/js/**")
			.antMatchers("/favicon.ico");
	}
	
}
