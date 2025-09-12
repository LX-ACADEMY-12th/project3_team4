package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.team4.mywebapp.dto.LoginDto;
import com.team4.mywebapp.dto.UserDto;
import com.team4.mywebapp.dto.UserInfoDto;

@Mapper
public interface UserMapper {
	
	// MyBatis가 이 메서드와 XML의 select 태그(id="loadUserInfo")를 연결함.
	// @Param으로 loginInUserId 라는 이름으로 매개변수를 지정	
	UserInfoDto loadUserInfo(@Param("loginUserId") String loginUserId);

	// 회원가입(전체 사용자 정보 필요)
	int insertUser(UserDto user);
	
	// 로그인 
	UserDto login(LoginDto logindto);
	
	// 정보 수정 (전체 사용자 정보 필요)
	int updateUserProfile(UserDto user);
	
	// 사용자 삭제 (user_id)
	int deleteUser(@Param("userId") int userId);
	
	// 사용자명으로 조회
	UserDto selectUserByUsername(@Param("username") String username);
	
	// ID로 사용자 조회
	UserDto selectUserById(@Param("userId") int userId);
	
}
