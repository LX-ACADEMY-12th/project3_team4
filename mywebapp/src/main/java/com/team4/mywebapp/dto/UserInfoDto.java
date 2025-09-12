package com.team4.mywebapp.dto;

public class UserInfoDto {
	
	private int userId;
	private String nickname;
	private String profileImagePath;
	private String todayMood;
	private String statusMessage;
	private String youtubeVideoId;
	private String backgroundColor;
	// 방문자 통계 DTO	
	private VisitCountDto visitCount;
	
	
	
	public VisitCountDto getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(VisitCountDto visitCount) {
		this.visitCount = visitCount;
	}
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
	public String getProfileImagePath() {
		return profileImagePath;
	}
	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}
	public String getTodayMood() {
		return todayMood;
	}
	public void setTodayMood(String todayMood) {
		this.todayMood = todayMood;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getYoutubeVideoId() {
		return youtubeVideoId;
	}
	public void setYoutubeVideoId(String youtubeVideoId) {
		this.youtubeVideoId = youtubeVideoId;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
