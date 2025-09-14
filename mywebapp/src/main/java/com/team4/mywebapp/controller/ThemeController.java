package com.team4.mywebapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team4.mywebapp.dto.ThemeDto;
import com.team4.mywebapp.service.ThemeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ThemeController {
	
	@Autowired
	private ThemeService themeService;
	
	@GetMapping("/theme-list")
	public ResponseEntity<List<ThemeDto>> list() {		
		List<ThemeDto> list = themeService.getAllThemes();
		return ResponseEntity.ok(list);		
	}
	
	@PostMapping("/theme-update")
	public ResponseEntity<Integer> update(@RequestBody ThemeDto themeDto) {
		int result = themeService.updateTheme(themeDto);
		return ResponseEntity.ok(result);
		
	}

}
