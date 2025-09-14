package com.team4.mywebapp.mapper;

import com.team4.mywebapp.dto.LoginDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper // MyBatis 매퍼 인터페이스임을 나타냅니다.
public interface LoginMapper {

    // 사용자의 아이디와 비밀번호가 일치하는지 확인하는 메서드입니다.
    // XML 파일의 <select id="authenticateUser">와 연결됩니다.
    LoginDto authenticateUser(@Param("loginId") String loginId, @Param("loginPw") String loginPw);
}