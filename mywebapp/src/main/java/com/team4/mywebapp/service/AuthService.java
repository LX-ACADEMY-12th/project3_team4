package com.team4.mywebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.team4.mywebapp.dto.UserDto;
import com.team4.mywebapp.mapper.UserMapper;

@Service
public class AuthService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    // 로그인
    public boolean authenticateUser(UserDto loginDto) {
    	// 1. 사용자 ID로 데이터베이스에서 사용자 정보 조회
        UserDto storedUser = userMapper.selectUserByUsername(loginDto.getLoginId());

        // 2. 사용자가 존재하지 않거나, 비밀번호가 일치하지 않으면 false 반환
        if (storedUser == null) {
            return false;
        }

        // 3. 저장된 암호화된 비밀번호와 입력된 비밀번호를 비교
        return passwordEncoder.matches(loginDto.getLoginPw(), storedUser.getLoginPw());
    }

}
