package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.team4.mywebapp.dto.UserDto;

@Mapper
public interface UserMapper {
	
	// 회원가입
	int inserUser(UserDto user);
	
	// 
}
