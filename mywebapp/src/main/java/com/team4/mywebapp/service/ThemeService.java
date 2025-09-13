package com.team4.mywebapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.ThemeDto;
import com.team4.mywebapp.mapper.ThemeMapper;

@Service
public class ThemeService {
	
	@Autowired
	private ThemeMapper themeMapper;
	
	// 테마 조회
	public List<ThemeDto> getAllThemes() {
		return themeMapper.getAllThemes();		
	}
	
	// 테마 수정
	public int updateTheme(ThemeDto themeDto) {
		return themeMapper.updateTheme(themeDto);		
	}
	

}
