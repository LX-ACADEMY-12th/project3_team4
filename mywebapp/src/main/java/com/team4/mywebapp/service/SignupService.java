package com.team4.mywebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.SignupDto;
import com.team4.mywebapp.mapper.SignupMapper;

@Service
public class SignupService {
	
	@Autowired
	private SignupMapper signupMapper;
    
    // 회원가입
	public boolean insertUser(SignupDto inputInfo) throws Exception{
	    try {
	        // 성공한다면 1 반환
	        int result = signupMapper.insertUser(inputInfo);
	        System.out.println("회원가입 성공");
	        // insert가 성공하면 1 이상의 값 반환
	        return result > 0;
	    } catch (Exception e) {
	        // 로그 출력
	        System.err.println("회원가입 실패: " + e.getMessage());
	        return false;
	    }
	}
	
	// 아이디 중복확인
	public boolean checkLoginIdExists(SignupDto inputInfo) throws Exception{
		try {
			// 성공한다면 1 반환
			int result = signupMapper.checkLoginIdExists(inputInfo.getLoginId());
			System.out.println(result);
			if (result > 0) {
				System.err.println("해당 아이디 중복 존재");
				return true;
			} else {
				System.out.println("사용가능 id");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("아이디 중복확인 실패: " + e.getMessage());
			return false;
		}
	}

}
