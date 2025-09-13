package com.team4.mywebapp.dto;

import java.time.LocalDateTime;

public class MinihomePageDTO {
    // minihome
    private int minihomeId;
    private String title;
    private String backgroundColor;   // FE에서 backgroundColor로 씀
    private int appliedThemeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // user
    private int userId;
    private String nickname;
    private String profileImage;      // FE에서 profileImage로 씀
    private String todayMood;
    private String statusMessage;
    private String birthDate;
    private String gender;
    private String region;
    private String hobby;

    // visit count (FE가 visitCount.todayCount / visitCount.totalCount로 씀)
    public static class VisitCount {
        private int todayCount;
        private int totalCount;
        public VisitCount() {}
        public VisitCount(int todayCount, int totalCount) {
            this.todayCount = todayCount; this.totalCount = totalCount;
        }
        public int getTodayCount() { return todayCount; }
        public void setTodayCount(int todayCount) { this.todayCount = todayCount; }
        public int getTotalCount() { return totalCount; }
        public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
    }
    private VisitCount visitCount;

    // 조회 쿼리에서 임시로 받는 값(서비스에서 visitCount로 묶어서 내려줌)
    private Integer todayCount;  // nullable
    private Integer totalCount;  // nullable

    // getters/setters
    public int getMinihomeId() { return minihomeId; }
    public void setMinihomeId(int minihomeId) { this.minihomeId = minihomeId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }
    public int getAppliedThemeId() { return appliedThemeId; }
    public void setAppliedThemeId(int appliedThemeId) { this.appliedThemeId = appliedThemeId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    public String getTodayMood() { return todayMood; }
    public void setTodayMood(String todayMood) { this.todayMood = todayMood; }
    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getHobby() { return hobby; }
    public void setHobby(String hobby) { this.hobby = hobby; }

    public VisitCount getVisitCount() { return visitCount; }
    public void setVisitCount(VisitCount visitCount) { this.visitCount = visitCount; }
    public Integer getTodayCount() { return todayCount; }
    public void setTodayCount(Integer todayCount) { this.todayCount = todayCount; }
    public Integer getTotalCount() { return totalCount; }
    public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }
}
