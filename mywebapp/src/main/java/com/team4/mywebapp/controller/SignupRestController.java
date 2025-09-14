package com.team4.mywebapp.controller;

import com.team4.mywebapp.dto.SignupDto;
import com.team4.mywebapp.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // 이 컨트롤러의 기본 URL 경로
@CrossOrigin(origins = "http://localhost:5173")
public class SignupRestController {

    @Autowired
    private SignupService signupService;

    /**
     * 아이디 중복확인 API입니다.
     * @param signupDto 클라이언트가 보낸 loginId를 담은 객체
     * @return 사용 가능 시 'available', 중복 시 'duplicate' 문자열 응답
     */
    @PostMapping("/checkDuplicate")
    public ResponseEntity<String> checkDuplicate(@RequestBody SignupDto signupDto) {
        // 서비스 계층을 통해 아이디 사용 가능 여부 확인
        boolean isAvailable = signupService.isIdAvailable(signupDto.getLoginId());
        
        if (isAvailable) {
            return ResponseEntity.ok("available");
        } else {
            return ResponseEntity.ok("duplicate");
        }
    }

    /**
     * 회원가입 처리 API입니다.
     * @param signupDto 클라이언트가 보낸 회원 정보를 담은 객체
     * @return 회원가입 성공 시 'success', 실패 시 'fail' 문자열 응답
     */
    @PostMapping("/insert")
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto) {
        // 서비스 계층을 통해 회원가입 로직 수행
        boolean isSuccess = signupService.signup(signupDto);
        
        if (isSuccess) {
            return ResponseEntity.ok("success");
        } else {
            // 중복된 아이디 또는 기타 이유로 실패 시
            return ResponseEntity.ok("fail");
        }
    }
}