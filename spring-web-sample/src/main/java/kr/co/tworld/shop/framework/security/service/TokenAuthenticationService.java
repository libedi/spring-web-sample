package kr.co.tworld.shop.framework.security.service;

import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.tworld.shop.framework.security.model.User;

/**
 * JWT Authentication Service
 * @author Sangjun, Park
 *
 */
@Service
public class TokenAuthenticationService {
	
	private final long EXPIRATION_TIME = 86_400_400;	// 1 day
	private final String TOKEN_PREFIX = "Bearer";
	private final String USERNAME = "username";
	private final String ROLE = "role";
	
	private final String SECRET;
	
	public TokenAuthenticationService(@Value("${token.key.secret}") final String secret) {
		this.SECRET = secret;
	}

	/**
	 * Create authentication token
	 * @param response
	 * @param user
	 */
	public void addAuthentication(final HttpServletResponse response, final User user) {
		final String jwt = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.signWith(SignatureAlgorithm.HS256, this.SECRET)
				.setSubject("TokenForSampleAuth")
				.setExpiration(Date.from(Instant.now().plusMillis(this.EXPIRATION_TIME)))
				.claim(this.USERNAME, user.getUsername())
				.claim(this.ROLE, user.getAuthorities().stream().findFirst().get().getAuthority())
				.compact();
				;
		response.addHeader(HttpHeaders.AUTHORIZATION, this.TOKEN_PREFIX + " " + jwt);
	}
	
	/**
	 * Get authentication info by JWT
	 * @param request
	 * @return
	 */
	public Authentication getAuthentication(final HttpServletRequest request) {
		final String token = StringUtils.contains(request.getQueryString(), "token") ?
				request.getParameter("token") : request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(StringUtils.isNotEmpty(token)) {
			final Claims claims = Jwts.parser()
					.setSigningKey(this.SECRET)
					.parseClaimsJws(StringUtils.replace(token, this.TOKEN_PREFIX, ""))
					.getBody();
			if(StringUtils.isNotEmpty(claims.get(this.USERNAME, String.class))) {
				final User user = User.builder()
						.username(claims.get(this.USERNAME, String.class))
						.password("")
						.role(claims.get(this.ROLE, String.class))
						.build();
				return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			}
		};
		return null;
	}

}
