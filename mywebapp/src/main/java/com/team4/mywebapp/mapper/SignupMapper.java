package com.team4.mywebapp.mapper;

import com.team4.mywebapp.dto.SignupDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper // MyBatis 매퍼 인터페이스임을 나타냅니다.
public interface SignupMapper {

    // 아이디 중복 여부를 확인하는 메서드
    int checkDuplicateId(@Param("loginId") String loginId);

    // 회원 정보를 데이터베이스에 삽입하는 메서드
    int insertUser(SignupDto signupDto);
}