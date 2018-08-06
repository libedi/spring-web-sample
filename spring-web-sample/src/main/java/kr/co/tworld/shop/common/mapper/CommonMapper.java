package kr.co.tworld.shop.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.co.tworld.shop.framework.security.model.User;

/**
 * Common Mapper interface
 * @author Sangjun, Park
 *
 */
@Mapper
public interface CommonMapper {

	@Select("SELECT username, password, role FROM USER WHERE username = #{username}")
	User getUser(@Param("username") String username);
}
