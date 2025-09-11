package com.team4.mywebapp.dto;
import java.time.LocalDateTime;
// 롬복은 컴파일 과정에서 자동으로 개발자가 원하는 메소드를 생성/주입 방식으로 동작하는 라이브러리
import lombok.Data;


public class UserDto {
	private int userId;
	private String loginId;
	private String loginPW;
	private String nickname;
	private String profilePhotoPath;
	private String statusMessage;
	private String birthDate;
	private String region;
	private String hobby;
	private String gender;
	
	public int getUserId() {
	    return userId;
	}

	public String getLoginId() {
	    return loginId;
	}

	public String getLoginPW() {
	    return loginPW;
	}

	public String getNickname() {
	    return nickname;
	}

	public String getProfilePhotoPath() {
	    return profilePhotoPath;
	}

	public String getStatusMessage() {
	    return statusMessage;
	}

	public String getBirthDate() {
	    return birthDate;
	}

	public String getRegion() {
	    return region;
	}

	public String getHobby() {
	    return hobby;
	}

	public String getGender() {
	    return gender;
	}

	// Setter methods
	public void setUserId(int userId) {
	    this.userId = userId;
	}

	public void setLoginId(String loginId) {
	    this.loginId = loginId;
	}

	public void setLoginPW(String loginPW) {
	    this.loginPW = loginPW;
	}

	public void setNickname(String nickname) {
	    this.nickname = nickname;
	}

	public void setProfilePhotoPath(String profilePhotoPath) {
	    this.profilePhotoPath = profilePhotoPath;
	}

	public void setStatusMessage(String statusMessage) {
	    this.statusMessage = statusMessage;
	}

	public void setBirthDate(String birthDate) {
	    this.birthDate = birthDate;
	}

	public void setRegion(String region) {
	    this.region = region;
	}

	public void setHobby(String hobby) {
	    this.hobby = hobby;
	}

	public void setGender(String gender) {
	    this.gender = gender;
	}
	
	@Override
	public String toString() {
	    return "User{" +
	            "userId=" + userId +
	            ", loginId='" + loginId + '\'' +
	            ", loginPW='" + loginPW + '\'' +
	            ", nickname='" + nickname + '\'' +
	            ", profilePhotoPath='" + profilePhotoPath + '\'' +
	            ", statusMessage='" + statusMessage + '\'' +
	            ", birthDate='" + birthDate + '\'' +
	            ", region='" + region + '\'' +
	            ", hobby='" + hobby + '\'' +
	            ", gender='" + gender + '\'' +
	            '}';
	}
	
}
