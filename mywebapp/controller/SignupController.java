package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.team4.mywebapp.dto.SignupDto;
import com.team4.mywebapp.service.SignupService;

@RestController
@RequestMapping("/api")  // ← API 경로
@CrossOrigin(origins = "http://localhost:3000")  // ← Vue.js 개발서버 CORS
public class SignupController {
	
	@Autowired
	private SignupService signupService;
	
	// 회원가입
	@PostMapping("/insert")
	public String insert(@RequestBody SignupDto inputUser) throws Exception{
	    try {
	        boolean isLoginIdExists = signupService.checkLoginIdExists(inputUser);

	        if (isLoginIdExists) {
	            System.err.println("loginId에서 중복 감지됨");
	            return "duplicate"; // redirect 대신 단순 문자열 반환
	        } else {
	            boolean isInserted = signupService.insertUser(inputUser);

	            if (isInserted) {
	                System.out.println("회원가입 성공");
	                return "success"; // redirect 대신 단순 문자열 반환
	            } else {
	                System.out.println("회원가입 실패");
	                return "fail"; // redirect 대신 단순 문자열 반환
	            }
	        }
	    } catch(Exception e) {
	        System.err.println("controller: 회원가입 오류: " + e.getMessage());
	        return "error"; // redirect 대신 단순 문자열 반환
	    }
	}

}
