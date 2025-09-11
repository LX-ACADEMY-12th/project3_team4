package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team4.mywebapp.dto.UserDto;
import com.team4.mywebapp.service.AuthService;



@RestController
@RequestMapping("/api")  // ← API 경로
@CrossOrigin(origins = "http://localhost:3000")  // ← Vue.js 개발서버 CORS
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	// 로그인 요청 처리
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto UserDto) {
        // 비즈니스 로직은 서비스 계층에 위임
        boolean isAuthenticated = authService.authenticateUser(UserDto);

        if (isAuthenticated) {
            // 인증 성공 시, 성공 응답 반환
            // (실제로는 JWT 토큰 등을 생성하여 반환)
            return ResponseEntity.ok("로그인 성공");
        } else {
            // 인증 실패 시, 401 Unauthorized 응답 반환
            return ResponseEntity.badRequest().body("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

}
