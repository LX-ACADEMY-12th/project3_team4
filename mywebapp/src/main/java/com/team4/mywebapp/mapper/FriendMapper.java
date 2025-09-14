package com.team4.mywebapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.team4.mywebapp.dto.UserDto;


@Mapper
public interface FriendMapper {

    /**
     * 전체 사용자 목록을 조회하는 매퍼 메소드
     * 
     * 실행할 SQL 쿼리 (FriendMapper.xml):
     * @return List<UserDto> 전체 사용자 목록
     */
    List<UserDto> getAllUsers();

	
}