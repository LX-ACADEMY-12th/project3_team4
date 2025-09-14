package com.team4.mywebapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team4.mywebapp.dto.UserDto;
import com.team4.mywebapp.service.FriendService;

@RestController
@RequestMapping("/api/friend")  // ← API 경로
@CrossOrigin(origins = "http://localhost:5173")
public class FriendRestController {
	 /**
     * 친구 관련 비즈니스 로직을 처리하는 서비스
     */
	@Autowired
	private FriendService friendService;
	
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            // 전체 사용자 목록을 조회
            List<UserDto> userList = friendService.getAllUsers();
            
            // 사용자 목록이 비어있는 경우
            if (userList.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            
            // 정상적으로 사용자 목록을 반환
            return ResponseEntity.ok(userList); // 200 OK
            
        } catch (Exception e) {
            // 서버 내부 오류 발생 시 500 에러 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

