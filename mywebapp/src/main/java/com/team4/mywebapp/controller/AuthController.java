package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team4.mywebapp.dto.LoginDto;
import com.team4.mywebapp.service.AuthService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")  // ← API 경로
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})  // ← Vue.js 개발서버 CORS
public class AuthController { 
	
	@Autowired
	private AuthService authService;
	
	// 로그인 요청 처리
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpSession session) {
		
		
		try {
			// 1. null체크
			if(loginDto == null) {
				return ResponseEntity.badRequest().body("로그인 정보가 없습니다.");
			}
			// 2. 아이디 검증
			if(loginDto.getLoginId() == null || loginDto.getLoginId().trim().isEmpty()) {
				return ResponseEntity.badRequest().body("아이디를 입력해주세요.");
			}
			// 3. 비밀번호 검증
			if(loginDto.getLoginPw() == null || loginDto.getLoginPw().trim().isEmpty()) {
				return ResponseEntity.badRequest().body("비밀번호를 입력해주세요.");
			}

	
			System.out.println("입력받은 아이디: " + loginDto.getLoginId());
			System.out.println("입력받은 비밀번호: " + loginDto.getLoginPw());
			
		
	        // 비즈니스 로직은 서비스 계층에 위임 AuthService를 사용해서 로그인 인증
	        boolean isAuthenticated = authService.authenticateUser(loginDto);
	
	        if (isAuthenticated) {
	            // 인증 성공 시, 성공 응답 반환
	            // (실제로는 JWT 토큰 등을 생성하여 반환)
	        	session.setAttribute("loginId", loginDto.getLoginId());
	            return ResponseEntity.ok("로그인 성공");
	        } else {
	            // 인증 실패 시, 401 Unauthorized 응답 반환
	            return ResponseEntity.badRequest().body("아이디 또는 비밀번호가 잘못되었습니다.");
	        }
	        
		} catch (Exception e) {
			System.out.println("로그인중 오류 발생" + e.getMessage());
			e.printStackTrace();			
			return ResponseEntity.badRequest().body("오류 발생.");					
		}
		
		// 로그아웃
		
    }
}

