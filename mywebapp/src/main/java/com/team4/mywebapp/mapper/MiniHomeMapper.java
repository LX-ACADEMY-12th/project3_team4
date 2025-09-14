package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.team4.mywebapp.dto.MiniHomeDto;

@Mapper //MyBatis 매퍼 인터페이스임을 나타냄.
public interface MiniHomeMapper {
	
	// 이 메서드는 MiniHomeMapper.xml 파일의 getMiniHomeByUserId id를 가진 <select> 태그와 연결됩니다.
	// @Param("userId")는 SQL 쿼리의 #{userId}와 매핑됨.
	MiniHomeDto getMiniHomeByUserId(@Param("userId") String userId);
	
//	int updateMiniHome(MiniHomeDto minihomeDto);

}
