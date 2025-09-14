package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.team4.mywebapp.dto.SignupDto;

@Mapper
public interface SignupMapper {
	// 회원가입
	int insertUser(SignupDto inputUser);
	
	// 아이디 중복확인
	int checkLoginIdExists(String loginId);
	
}
