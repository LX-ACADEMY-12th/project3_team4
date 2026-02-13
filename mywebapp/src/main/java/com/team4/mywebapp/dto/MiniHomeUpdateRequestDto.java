// MiniHomeUpdateRequestDto.java
package com.team4.mywebapp.dto;

import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

/**
 * 미니홈피 업데이트 요청 DTO
 * 프론트엔드에서 미니홈피 정보 수정 요청시 사용하는 데이터 전송 객체
 */
public class MiniHomeUpdateRequestDto {
    
    private Long userId;
    private String miniHomeOwnerLoginId;
	private String todayMood;
    private String statusMessage;
    private LocalDate birthDate;
    private String gender;
    private String region;
    private String hobby;
    private String youtubeVideoId;
    private String backgroundColor;
    private Integer appliedThemeId;
    private MultipartFile profileImageFile;
    private String currentProfileImageUrl;
    
    // 기본 생성자
    public MiniHomeUpdateRequestDto() {
        this.backgroundColor = "#f8f9fa"; // 기본 배경색
    }
    
    // 모든 필드를 받는 생성자
    public MiniHomeUpdateRequestDto(Long userId, String miniHomeOwnerLoginId, String todayMood, String statusMessage,
                                   LocalDate birthDate, String gender, String region,
                                   String hobby, String youtubeVideoId, String backgroundColor,
                                   Integer appliedThemeId, MultipartFile profileImageFile,
                                   String currentProfileImageUrl) {
        this.userId = userId;
        this.miniHomeOwnerLoginId = miniHomeOwnerLoginId;
        this.todayMood = todayMood;
        this.statusMessage = statusMessage;
        this.birthDate = birthDate;
        this.gender = gender;
        this.region = region;
        this.hobby = hobby;
        this.youtubeVideoId = youtubeVideoId;
        this.backgroundColor = backgroundColor != null ? backgroundColor : "#f8f9fa";
        this.appliedThemeId = appliedThemeId;
        this.profileImageFile = profileImageFile;
        this.currentProfileImageUrl = currentProfileImageUrl;
    }
    
    // Getter 메서드들
    public Long getUserId() {
        return userId;
    }

    public String getMiniHomeOwnerLoginId() {
		return miniHomeOwnerLoginId;
	}

    
    public String getTodayMood() {
        return todayMood;
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getRegion() {
        return region;
    }
    
    public String getHobby() {
        return hobby;
    }
    
    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }
    
    public String getBackgroundColor() {
        return backgroundColor;
    }
    
    public Integer getAppliedThemeId() {
        return appliedThemeId;
    }
    
    public MultipartFile getProfileImageFile() {
        return profileImageFile;
    }
    
    public String getCurrentProfileImageUrl() {
        return currentProfileImageUrl;
    }
    
    // Setter 메서드들
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
	public void setMiniHomeOwnerLoginId(String miniHomeOwnerLoginId) {
		this.miniHomeOwnerLoginId = miniHomeOwnerLoginId;
	}
	
    public void setTodayMood(String todayMood) {
        this.todayMood = todayMood;
    }
    
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    
    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }
    
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    public void setAppliedThemeId(Integer appliedThemeId) {
        this.appliedThemeId = appliedThemeId;
    }
    
    public void setProfileImageFile(MultipartFile profileImageFile) {
        this.profileImageFile = profileImageFile;
    }
    
    public void setCurrentProfileImageUrl(String currentProfileImageUrl) {
        this.currentProfileImageUrl = currentProfileImageUrl;
    }
}