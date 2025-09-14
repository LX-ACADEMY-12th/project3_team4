package com.team4.mywebapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.team4.mywebapp.dto.FriendDto;

@Mapper
public interface FriendMapper {

	List<FriendDto> getAllUsers(String loginId);
	
}