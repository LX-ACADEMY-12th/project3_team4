package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.team4.mywebapp.dto.UserDto;

@Mapper
public interface UserMapper {
	
	// 회원가입(전체 사용자 정보 필요)
	int insertUser(UserDto user);
	
	// 로그인 
	UserDto login(UserDto dto);
	
	// 정보 수정 (전체 사용자 정보 필요)
	int updateUserProfile(UserDto user);
	
	// 사용자 삭제 (user_id)
	int deleteUser(@Param("userId") int userId);
	
	// 닉네임 중복
	int checkNicknameExists(@Param("nickname") String nickname);
	
	// 사용자명으로 조회
	UserDto selectUserByUsername(@Param("username") String username);
	
	// ID로 사용자 조회
	UserDto selectUserById(@Param("userId") int userId);
	
}
