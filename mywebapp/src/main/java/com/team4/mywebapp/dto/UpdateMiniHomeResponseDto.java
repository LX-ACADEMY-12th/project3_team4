package com.team4.mywebapp.dto;

public class UpdateMiniHomeResponseDto {
    private boolean success;
    private String message;
    private String profileImageUrl; // 추가
    private MiniHomeDto updatedMiniHomeInfo; // 이 필드 추가

    // 빌더 객체를 반환하는 정적 팩토리 메서드
    public static Builder builder() {
        return new Builder();
    }

    // private 생성자로 외부에서의 직접 생성을 막음
    private UpdateMiniHomeResponseDto(Builder builder) {
        this.success = builder.success;
        this.message = builder.message;
        this.profileImageUrl = builder.profileImageUrl;
        this.updatedMiniHomeInfo = builder.updatedMiniHomeInfo; // 빌더로부터 값 할당
    }

    // Getter 메서드
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public MiniHomeDto getUpdatedMiniHomeInfo() {
        return updatedMiniHomeInfo;
    }

    public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	// 내부 static Builder 클래스
    public static class Builder {
        private boolean success;
        private String message;
        private MiniHomeDto updatedMiniHomeInfo; // 빌더에도 필드 추가
		private String profileImageUrl;

        private Builder() {}

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }
        
        public Builder profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public Builder updatedMiniHomeInfo(MiniHomeDto updatedMiniHomeInfo) {
            this.updatedMiniHomeInfo = updatedMiniHomeInfo;
            return this;
        }

        public UpdateMiniHomeResponseDto build() {
            return new UpdateMiniHomeResponseDto(this);
        }
    }
}