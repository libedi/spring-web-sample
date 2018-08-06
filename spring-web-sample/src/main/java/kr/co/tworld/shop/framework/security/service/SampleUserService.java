package kr.co.tworld.shop.framework.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.tworld.shop.common.service.CommonService;

/**
 * Sample UserDetailsService
 * @author Sangjun, Park
 *
 */
@Service
public class SampleUserService implements UserDetailsService {
	
	@Autowired
	private CommonService commonService;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return Optional.ofNullable(this.commonService.getUser(username))
				.orElseThrow(() -> {
			return new UsernameNotFoundException("Not found username: " + username);
		});
	}

}
