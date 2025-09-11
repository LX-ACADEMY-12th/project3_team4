package com.team4.mywebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.UserDto;
import com.team4.mywebapp.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 회원가입
    public void registerUser(UserDto user) {
        // 1. 중복 체크
        
        // 2. 비밀번호 암호화
        
        // 3. 사용자 등록
    }

}
