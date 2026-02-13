package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team4.mywebapp.dto.LoginDto;
import com.team4.mywebapp.dto.UserDto;
import com.team4.mywebapp.service.LoginService;

@RestController // RESTful API를 위한 컨트롤러
@RequestMapping("/api") // 이 컨트롤러의 기본 URL 경로
@CrossOrigin(origins = "http://localhost:5173")
public class LoginRestController {

	// Spring이 LoginService의 인스턴스를 자동 주입
	@Autowired
	private LoginService loginService;

	/**
	 * HTTP POST 요청을 받아 로그인 처리를 수행하는 API입니다.
	 * 
	 * @param loginDto 클라이언트에서 전송한 로그인 정보 (JSON 형식)
	 * @return 로그인 성공 시 200 OK, 실패 시 401 Unauthorized
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		// loginDto 파라미터가 잘 받아와지는지 확인하는 로그 출력
        System.out.println("요청받은 로그인 id : " + loginDto.getLoginId()); 
        System.out.println("요청받은 로그인 패스워드 : " + loginDto.getLoginPw()); 
        
		// 서비스 계층의 로그인 메서드를 호출하여 사용자 인증을 시도
		UserDto authenticatedUser = loginService.login(loginDto);

		// 인증 성공 여부 확인
		if (authenticatedUser != null) {
			// 로그인 성공 시 사용자 정보를 JSON으로 변환
			return ResponseEntity.ok(authenticatedUser);
		} else {
			// 로그인 실패 시 401 Unauthorized 응답
			return ResponseEntity.status(401).body("로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
		}

	}
}