package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.team4.mywebapp.dto.MiniHomeDto;

@Mapper
public interface MiniHomeMapper {
	
	
	MiniHomeDto getMiniHomeById(@Param("minihomeId") int minihomeOwnerId);
	
	int updateMiniHome(MiniHomeDto minihomeDto);

}
