package com.team4.mywebapp.dto;


public class UserUpdateDto {
    private Long userId;
    private String todayMood;
    private String statusMessage;
    private String birthDate;
    private String gender;
    private String region;
    private String hobby;
    private String profileImageFile;

    // 1. 빌더 객체를 생성하는 public static 메서드
    public static Builder builder() {
        return new Builder();
    }

    // 2. 외부에서 직접 생성자를 호출할 수 없도록 private 생성자 선언
    private UserUpdateDto(Builder builder) {
        this.userId = builder.userId;
        this.todayMood = builder.todayMood;
        this.statusMessage = builder.statusMessage;
        this.birthDate = builder.birthDate;
        this.gender = builder.gender;
        this.region = builder.region;
        this.hobby = builder.hobby;
        this.profileImageFile = builder.profileImageFile;
    }

    // 3. Getter와 Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getProfileImageFile() {
        return profileImageFile;
    }

    public void setProfileImageFile(String profileImageFile) {
        this.profileImageFile = profileImageFile;
    }

    // 4. static 내부 클래스로 Builder 구현
    public static class Builder {
        private Long userId;
        private String todayMood;
        private String statusMessage;
        private String birthDate;
        private String gender;
        private String region;
        private String hobby;
        private String profileImageFile;

        private Builder() {}

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder todayMood(String todayMood) {
            this.todayMood = todayMood;
            return this;
        }

        public Builder statusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public Builder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder hobby(String hobby) {
            this.hobby = hobby;
            return this;
        }

        public Builder profileImageFile(String multipartFile) {
            this.profileImageFile = multipartFile;
            return this;
        }

        // 최종적으로 UserUpdateDto 객체를 생성하여 반환하는 메서드
        public UserUpdateDto build() {
            return new UserUpdateDto(this);
        }
    }
}