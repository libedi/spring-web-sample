package kr.co.tworld.shop.framework.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * User
 * @author Sangjun, Park
 *
 */
@Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

	private static final long serialVersionUID = -3417934467310622386L;
	
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authotities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authotities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
