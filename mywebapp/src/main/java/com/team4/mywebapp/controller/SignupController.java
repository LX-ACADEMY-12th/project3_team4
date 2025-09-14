package com.team4.mywebapp.controller;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team4.mywebapp.dto.SignupDto;
import com.team4.mywebapp.service.SignupService;

@RestController
@RequestMapping("/api") // ← API 경로
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"}) // ← Vue.js 개발서버 CORS
public class SignupController {

    @Autowired
    private SignupService signupService;

    // 중복확인된 아이디들을 저장하는 Map
    private static final Map<String, Boolean> checkedLoginIds = new ConcurrentHashMap<>();

    // 아이디 중복확인
    @PostMapping("/checkDuplicate")
    public String checkDuplicate(@RequestBody SignupDto inputUser) throws Exception {
        try {
            String loginId = inputUser.getLoginId();
            boolean isLoginIdExists = signupService.checkLoginIdExists(inputUser);
            
            if (isLoginIdExists) {
                System.out.println("아이디 중복: " + loginId);
                checkedLoginIds.remove(loginId); // 중복된 아이디는 체크 목록에서 제거
                return "duplicate";
            } else {
                System.out.println("아이디 사용 가능: " + loginId);
                checkedLoginIds.put(loginId, true); // 사용 가능한 아이디를 체크 목록에 추가
                return "available";
            }
        } catch(Exception e) {
            System.err.println("controller: 중복확인 오류: " + e.getMessage());
            return "error";
        }
    }

    // 회원가입
    @PostMapping("/insert")
    public String insert(@RequestBody SignupDto inputUser) throws Exception{
        try {
            String loginId = inputUser.getLoginId();
            
            // 해당 아이디가 중복확인을 거쳤는지 확인
            if (!checkedLoginIds.containsKey(loginId) || !checkedLoginIds.get(loginId)) {
                System.out.println("중복확인 미완료: " + loginId);
                return "unchecked";
            }
            
            // 회원가입 진행
            boolean isInserted = signupService.insertUser(inputUser);
            if (isInserted) {
                System.out.println("회원가입 성공: " + loginId);
                checkedLoginIds.remove(loginId); // 성공 후 체크 목록에서 제거
                return "success";
            } else {
                System.out.println("회원가입 실패: " + loginId);
                return "fail";
            }

        } catch(Exception e) {
            System.err.println("controller: 회원가입 오류: " + e.getMessage());
            return "error";
        }
    }
}