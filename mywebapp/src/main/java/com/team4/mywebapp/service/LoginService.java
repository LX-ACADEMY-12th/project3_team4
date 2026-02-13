package com.team4.mywebapp.service;

import com.team4.mywebapp.dto.LoginDto;
import com.team4.mywebapp.dto.UserDto;
import com.team4.mywebapp.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 이 클래스가 서비스 컴포넌트임을 나타냅니다.
public class LoginService {

    @Autowired // Spring이 LoginMapper의 인스턴스를 주입해줍니다.
    private LoginMapper loginMapper;

    /**
     * 사용자가 제공한 로그인 정보로 인증을 시도하는 비즈니스 메서드입니다.
     * @param loginDto 사용자의 아이디와 비밀번호를 담은 객체
     * @return 인증 성공 시 LoginDto 객체, 실패 시 null
     */
    public UserDto login(LoginDto loginDto) {
        // 매퍼를 호출하여 데이터베이스에서 일치하는 사용자를 찾습니다.
    	UserDto authenticatedUser = loginMapper.authenticateUser(loginDto.getLoginId(), loginDto.getLoginPw());

        // 사용자 정보가 존재하면 인증 성공, 없으면 null 반환
        return authenticatedUser;
    }
}