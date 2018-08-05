package kr.co.tworld.shop.common.service;

import org.springframework.stereotype.Service;

import kr.co.tworld.shop.common.mapper.CommonMapper;
import kr.co.tworld.shop.framework.security.model.User;
import lombok.RequiredArgsConstructor;

/**
 * Common Service
 * @author Sangjun, Park
 *
 */
@Service
@RequiredArgsConstructor
public class CommonService {
	
	private final CommonMapper commonMapper;
	
	/**
	 * Get User info
	 * @param username
	 * @return
	 */
	public User getUser(final String username) {
		return this.commonMapper.getUser(username);
	}
	
}
