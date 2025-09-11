package com.team4.mywebapp.dto;

import java.util.Date;
// 롬복은 컴파일 과정에서 자동으로 개발자가 원하는 메소드를 생성/주입 방식으로 동작하는 라이브러리
//import lombok.Data;

//@Data
public class UserDto {
	
	private int userId;
	private String loginId;
	private String loginPw;
	private String nickname;
	private String minime;
	private String profilePhotoPath;
	private String statusMessage;
	private Date birthDate;
	private String region;
	private String hobby;
	private String gender;
	
	public UserDto() { }
		
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginPw() {
		return loginPw;
	}
	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMinime() {
		return minime;
	}
	public void setMinime(String minime) {
		this.minime = minime;
	}
	public String getProfilePhotoPath() {
		return profilePhotoPath;
	}
	public void setProfilePhotoPath(String profilePhotoPath) {
		this.profilePhotoPath = profilePhotoPath;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	}
	

