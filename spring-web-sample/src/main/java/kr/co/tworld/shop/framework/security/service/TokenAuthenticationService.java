package kr.co.tworld.shop.framework.security.service;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import kr.co.tworld.shop.framework.security.model.User;

/**
 * JWT Authentication Service
 * @author Sangjun, Park
 *
 */
@Service
public class TokenAuthenticationService {
	
	private final long EXPIRATION_TIME = 86_400_400;	// 1 day
	private final String HEADER = "Authorization";
	private final String TOKEN_PREFIX = "Bearer";
	private final String USERNAME = "username";
	
	private final String SECRET;
	
	public TokenAuthenticationService(@Value("${token.key.secret}") String secret) {
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
				.setSubject("TokenForSampleAuth")
				.setExpiration(Date.from(Instant.now().plusMillis(this.EXPIRATION_TIME)))
				.claim(this.USERNAME, user.getUsername())
				.compact();
				;
		response.addHeader(this.HEADER, this.TOKEN_PREFIX + " " + jwt);
	}
	
	/**
	 * Get authentication info by JWT
	 * @param request
	 * @return
	 */
	public Authentication getAuthentication(final HttpServletRequest request) {
		final String token = request.getHeader(this.HEADER);
		
		if(StringUtils.isNotEmpty(token)) {
			final Claims claims = Jwts.parser()
					.setSigningKey(this.SECRET)
					.parseClaimsJwt(StringUtils.replace(token, this.TOKEN_PREFIX, ""))
					.getBody();
			if(StringUtils.isNotEmpty(claims.get(this.USERNAME, String.class))) {
				final User user = User.builder()
						.username(claims.get(this.USERNAME, String.class))
						.password("")
						.authotities(Collections.emptyList())
						.build();
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		};
		return null;
	}

}
