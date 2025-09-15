package com.team4.mywebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.UserModifyDto;
import com.team4.mywebapp.mapper.UserModifyMapper;

@Service
public class UserModifyService {
	
	@Autowired
	private UserModifyMapper usermodifyMapper;
	
	/**
     * 사용자 정보 조회
     * @param  minihome_id
     * @return 사용자 정보 Dto
     */
	public UserModifyDto getUserProfile(int minihomeId) {
		return usermodifyMapper.getUserProfile(minihomeId);
		
	}
	
	/**
     * 사용자 정보 업데이트
     * @param 수정할 사용자 정보 Dto
     * @return 업데이트 된 행의 개수
     */
	public int updateUser(UserModifyDto userModifyDto) {
		return usermodifyMapper.updateUser(userModifyDto);
		
	}
	
	 /**
     * loginId로 사용자 정보 조회
     * @param  loginId
     * @return 사용자 정보 Dto
     */
   public UserModifyDto getUserProfileInLoginId(String loginId) {
      return usermodifyMapper.getUserProfileInLoginId(loginId);
   }
	
	/**
     * 
     * @param 
     * @return 
     */
	public int updateUserProfilePhoto(UserModifyDto userModifyDto) {
	    return usermodifyMapper.updateUserProfilePhoto(userModifyDto);
	}
	

}
