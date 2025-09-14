package com.team4.mywebapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team4.mywebapp.dto.FriendDTO;
import com.team4.mywebapp.service.FriendService;

@RestController
@RequestMapping("/api")  // ← API 경로
@CrossOrigin(origins = "http://localhost:5173")
public class FriendController {

	@Autowired
	private FriendService friendService;
	
	@GetMapping("/show-users")
	public ResponseEntity<List<FriendDTO>> list(@RequestParam int miniHomeOwnerId){
		List<FriendDTO> list = friendService.getAllUsers(miniHomeOwnerId);
		return ResponseEntity.ok(list);
	}
}
