package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.team4.mywebapp.dto.UserInfoDto;

@Mapper
public interface UserMapper {
	
	// MyBatis가 이 메서드와 XML의 select 태그(id="loadUserInfo")를 연결함.
	// @Param으로 loginInUserId 라는 이름으로 매개변수를 지정	
	UserInfoDto loadUserInfo(@Param("loginUserId") String loginUserId);
}
