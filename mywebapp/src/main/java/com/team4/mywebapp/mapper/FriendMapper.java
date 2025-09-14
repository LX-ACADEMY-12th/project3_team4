package com.team4.mywebapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.team4.mywebapp.dto.FriendDTO;

@Mapper
public interface FriendMapper {

	List<FriendDTO> getAllUsers(int miniHomeOwnerId);
	
}
