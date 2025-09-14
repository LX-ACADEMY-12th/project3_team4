package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Param;

import com.team4.mywebapp.dto.UserModifyDto;

public interface UserModifyMapper {

	/**
     * minihome_id로 사용자 정보 조회
     * @param  minihome_id
     * @return 사용자 정보 Dto
     */
	UserModifyDto getUserProfile(@Param("minihomeId") int minihomeId);

	// 사용자 정보 업데이트
	// param: 수정할 사용자 정보 Dto
	// return: 업데이트 된 행의 개수
	int updateUser(UserModifyDto userModityDto);

}
