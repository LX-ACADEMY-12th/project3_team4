package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.team4.mywebapp.dto.MinimeDto;

@Mapper
public interface MinimeMapper {
	// 회원가입
	MinimeDto getMinime(int minimeId);
	
	// 아이디 중복확인
	void checkLoginIdExists(MinimeDto dto);
	
}
