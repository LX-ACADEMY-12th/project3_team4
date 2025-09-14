package com.team4.mywebapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.FriendDTO;
import com.team4.mywebapp.mapper.FriendMapper;

@Service
public class FriendService {

	@Autowired
	private FriendMapper friendMapper;
	
	public List<FriendDTO> getAllUsers(int miniHomeOwnerId) {
		return friendMapper.getAllUsers(miniHomeOwnerId);
	}
	
}
