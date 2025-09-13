package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team4.mywebapp.dto.MiniHomeDto;
import com.team4.mywebapp.service.MiniHomeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class MIniHomeController {
	
	@Autowired
	private MiniHomeService minihomeService;
	
	@PostMapping("/minihome-get")
	public ResponseEntity<MiniHomeDto> get(@RequestBody MiniHomeDto minihomeDto) {
		MiniHomeDto result = minihomeService.getMiniHomeById(minihomeDto.getMinihomeId());
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/minihome-update")
	public ResponseEntity<Integer> update(@RequestBody MiniHomeDto minihomeDto) {
		int result = minihomeService.updateMiniHome(minihomeDto);
		return ResponseEntity.ok(result);		
	}
	
	
}
