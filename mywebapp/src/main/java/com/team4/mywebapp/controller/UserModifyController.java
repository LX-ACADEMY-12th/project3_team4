package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team4.mywebapp.dto.UserModifyDto;
import com.team4.mywebapp.service.UserModifyService;

@RestController
@RequestMapping("/api")
public class UserModifyController {
	
	@Autowired
	private UserModifyService usermodifyService;
	
	/**
     * 사용자 정보 조회하는 api
     * @param  minihome_id
     * @return 사용자 정보 Dto
     */
	@GetMapping("/user-profile")
	public ResponseEntity<UserModifyDto> getUserProfile(@RequestParam int minihomeId) {
		UserModifyDto userProfile = usermodifyService.getUserProfile(minihomeId);
		return ResponseEntity.ok(userProfile);		
	}
	
	/**
     * 사용자 정보 업데이트
     * @param 수정할 사용자 정보 Dto
     * @return 업데이트 된 행의 개수
     */
	@PostMapping("/user-update")
	public ResponseEntity<String> updateUser(@RequestBody UserModifyDto usermodifyDto) {
		int updateRows = usermodifyService.updateUser(usermodifyDto);
		if (updateRows > 0) {
			return ResponseEntity.ok("success");
		} else {
			return ResponseEntity.status(500).body("fail");	
	}
 }
}
