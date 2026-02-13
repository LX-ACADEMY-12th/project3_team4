package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.team4.mywebapp.dto.MiniHomeDto;
import com.team4.mywebapp.dto.MiniHomeInsertDto;
import com.team4.mywebapp.dto.MiniHomeUpdateDto;

@Mapper //MyBatis 매퍼 인터페이스임을 나타냄.
public interface MiniHomeMapper {
	
	// 이 메서드는 MiniHomeMapper.xml 파일의 getMiniHomeByUserId id를 가진 <select> 태그와 연결됩니다.
	// @Param("userId")는 SQL 쿼리의 #{userId}와 매핑됨.
	MiniHomeDto getMiniHomeByUserId(@Param("userId") String userId);
	
    /**
     * 새로운 미니홈피 정보를 DB에 삽입합니다.
     * @param miniHomeDto 삽입할 미니홈피 정보 DTO
     */
    void insertMiniHome(MiniHomeInsertDto miniHomeInsertDto);

    /**
     * 기존 미니홈피 정보를 업데이트합니다.
     * @param miniHomeUpdateDto 업데이트할 미니홈피 정보 DTO
     */
    void updateMiniHome(MiniHomeUpdateDto miniHomeUpdateDto);
	void updateBgm(MiniHomeUpdateDto miniHomeUpdateDto);


}
