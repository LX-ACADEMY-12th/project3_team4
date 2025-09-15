// MusicItemDto.java
package com.team4.mywebapp.dto;

/**
 * 개별 음악 아이템 DTO
 * YouTube에서 검색된 각 음악 비디오의 정보를 담는 데이터 전송 객체
 */
public class MusicItemDto {
    
    private String videoId;
    private String title;
    private String channelTitle;
    private String description;
    private String thumbnail;
    private String publishedAt;
    private Long viewCount;
    private String duration;
    private Double qualityScore;
    private String genre;
    private boolean isFavorite;
    
    // 기본 생성자
    public MusicItemDto() {
        this.isFavorite = false;
    }
    
    // 주요 필드를 받는 생성자
    public MusicItemDto(String videoId, String title, String channelTitle, 
                       String description, String thumbnail, String publishedAt) {
        this.videoId = videoId;
        this.title = title;
        this.channelTitle = channelTitle;
        this.description = description;
        this.thumbnail = thumbnail;
        this.publishedAt = publishedAt;
        this.isFavorite = false;
    }
    
    // 모든 필드를 받는 생성자
    public MusicItemDto(String videoId, String title, String channelTitle, String description,
                       String thumbnail, String publishedAt, Long viewCount, String duration,
                       Double qualityScore, String genre, boolean isFavorite) {
        this.videoId = videoId;
        this.title = title;
        this.channelTitle = channelTitle;
        this.description = description;
        this.thumbnail = thumbnail;
        this.publishedAt = publishedAt;
        this.viewCount = viewCount;
        this.duration = duration;
        this.qualityScore = qualityScore;
        this.genre = genre;
        this.isFavorite = isFavorite;
    }
    
    // Getter 메서드들
    public String getVideoId() {
        return videoId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getChannelTitle() {
        return channelTitle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getThumbnail() {
        return thumbnail;
    }
    
    public String getPublishedAt() {
        return publishedAt;
    }
    
    public Long getViewCount() {
        return viewCount;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public Double getQualityScore() {
        return qualityScore;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    // Setter 메서드들
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    
    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    // Builder 패턴 구현
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String videoId;
        private String title;
        private String channelTitle;
        private String description;
        private String thumbnail;
        private String publishedAt;
        private Long viewCount;
        private String duration;
        private Double qualityScore;
        private String genre;
        private boolean isFavorite = false;
        
        public Builder videoId(String videoId) {
            this.videoId = videoId;
            return this;
        }
        
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        
        public Builder channelTitle(String channelTitle) {
            this.channelTitle = channelTitle;
            return this;
        }
        
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        
        public Builder thumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }
        
        public Builder publishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }
        
        public Builder viewCount(Long viewCount) {
            this.viewCount = viewCount;
            return this;
        }
        
        public Builder duration(String duration) {
            this.duration = duration;
            return this;
        }
        
        public Builder qualityScore(Double qualityScore) {
            this.qualityScore = qualityScore;
            return this;
        }
        
        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }
        
        public Builder isFavorite(boolean isFavorite) {
            this.isFavorite = isFavorite;
            return this;
        }
        
        public MusicItemDto build() {
            return new MusicItemDto(videoId, title, channelTitle, description, thumbnail,
                                   publishedAt, viewCount, duration, qualityScore, genre, isFavorite);
        }
    }
}