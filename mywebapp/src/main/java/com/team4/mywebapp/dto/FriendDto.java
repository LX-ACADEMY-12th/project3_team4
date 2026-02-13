package com.team4.mywebapp.dto;

public class FriendDto {

	private int userId; // 사용자 아이디
	private String nickname; // 사용자 닉네임
	private String loginId; // 로그인 아이디
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	
}
