package com.team4.mywebapp.service;

import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.UserInfoDto;
import com.team4.mywebapp.mapper.UserMapper;

@Service
public class UserService {

	private final UserMapper userMapper;

	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public UserInfoDto getUserInfo(String loginUserId) {

		try {
			// 매퍼를 호출하여 로그인ID로 데이터베이스에서 사용자 정보를 가져옵니다.
			UserInfoDto userInfoDto = userMapper.loadUserInfo(loginUserId);

			// 데이터가 없는 경우를 대비한 Null 체크
			if (userInfoDto == null) {
				return null;
			}

			return userInfoDto;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
