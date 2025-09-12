package com.team4.mywebapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team4.mywebapp.dto.UserInfoDto;
import com.team4.mywebapp.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	// 간단한 테스트 API 추가
    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok("안녕");
    }

	@GetMapping("/my-minihome")
	public ResponseEntity<UserInfoDto> getUserInfo(@RequestParam("loginUserId") String loginUserId) {

		UserInfoDto userInfo = userService.getUserInfo(loginUserId);

		if (userInfo == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(userInfo);
	}
}
