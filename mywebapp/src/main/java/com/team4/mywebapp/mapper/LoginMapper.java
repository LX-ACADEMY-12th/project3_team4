package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.team4.mywebapp.dto.LoginDto;
import com.team4.mywebapp.dto.UserDto;

@Mapper
public interface LoginMapper {
	
	// 로그인 
	UserDto login(LoginDto logindto);

	UserDto selectUserByUsername(@Param("loginId") String loginId);
	
	
}
