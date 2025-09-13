package com.team4.mywebapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.team4.mywebapp.dto.ThemeDto;

@Mapper
public interface ThemeMapper {
	
	List<ThemeDto> getAllThemes();
	
	int updateTheme(ThemeDto themeDto);

}
