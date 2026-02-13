package com.team4.mywebapp.dto;

import java.time.LocalDateTime;

public class MiniHomeUpdateDto {
    private String miniHomeOwnerLoginId;
    private String youtubeVideoId;
    private String backgroundColor;
    private String appliedThemeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 이전에 DTO에 없던 필드이므로 추가합니다.
    private Long userId;
    private String todayMood;
    private String statusMessage;
    private String birthDate;
    private String gender;
    private String region;
    private String hobby;

    public static Builder builder() {
        return new Builder();
    }

    private MiniHomeUpdateDto(Builder builder) {
        this.miniHomeOwnerLoginId = builder.miniHomeOwnerLoginId;
        this.youtubeVideoId = builder.youtubeVideoId;
        this.backgroundColor = builder.backgroundColor;
        this.appliedThemeId = builder.appliedThemeId;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.userId = builder.userId;
        this.todayMood = builder.todayMood;
        this.statusMessage = builder.statusMessage;
        this.birthDate = builder.birthDate;
        this.gender = builder.gender;
        this.region = builder.region;
        this.hobby = builder.hobby;
    }

    // 모든 필드에 대한 Getter와 Setter
    public String getMiniHomeOwnerLoginId() { return miniHomeOwnerLoginId; }
    public void setMiniHomeOwnerLoginId(String miniHomeOwnerLoginId) { this.miniHomeOwnerLoginId = miniHomeOwnerLoginId; }
    public String getYoutubeVideoId() { return youtubeVideoId; }
    public void setYoutubeVideoId(String youtubeVideoId) { this.youtubeVideoId = youtubeVideoId; }
    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }
    public String getAppliedThemeId() { return appliedThemeId; }
    public void setAppliedThemeId(String appliedThemeId) { this.appliedThemeId = appliedThemeId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
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

    public static class Builder {
        private String miniHomeOwnerLoginId;
        private String youtubeVideoId;
        private String backgroundColor;
        private String appliedThemeId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long userId;
        private String todayMood;
        private String statusMessage;
        private String birthDate;
        private String gender;
        private String region;
        private String hobby;

        private Builder() {}
        
        public Builder miniHomeOwnerLoginId(String miniHomeOwnerLoginId) { this.miniHomeOwnerLoginId = miniHomeOwnerLoginId; return this; }
        public Builder youtubeVideoId(String youtubeVideoId) { this.youtubeVideoId = youtubeVideoId; return this; }
        public Builder backgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; return this; }
        public Builder appliedThemeId(String appliedThemeId) { this.appliedThemeId = appliedThemeId; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder todayMood(String todayMood) { this.todayMood = todayMood; return this; }
        public Builder statusMessage(String statusMessage) { this.statusMessage = statusMessage; return this; }
        public Builder birthDate(String birthDate) { this.birthDate = birthDate; return this; }
        public Builder gender(String gender) { this.gender = gender; return this; }
        public Builder region(String region) { this.region = region; return this; }
        public Builder hobby(String hobby) { this.hobby = hobby; return this; }

        public MiniHomeUpdateDto build() {
            return new MiniHomeUpdateDto(this);
        }
    }
}