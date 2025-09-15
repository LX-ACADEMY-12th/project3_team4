// MusicSearchResponseDto.java
package com.team4.mywebapp.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 음악 검색 응답 DTO
 * 클라이언트에게 검색 결과를 반환할 때 사용하는 데이터 전송 객체
 */
public class MusicSearchResponseDto {
    
    private boolean success;
    private String message;
    private List<MusicItemDto> items;
    private int totalResults;
    private LocalDateTime searchedAt;
    
    // 기본 생성자
    public MusicSearchResponseDto() {
        this.items = new ArrayList<>();
    }
    
    // 모든 필드를 받는 생성자
    public MusicSearchResponseDto(boolean success, String message, List<MusicItemDto> items, 
                                 int totalResults, LocalDateTime searchedAt) {
        this.success = success;
        this.message = message;
        this.items = items != null ? items : new ArrayList<>();
        this.totalResults = totalResults;
        this.searchedAt = searchedAt;
    }
    
    // Getter 메서드들
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public List<MusicItemDto> getItems() {
        return items;
    }
    
    public int getTotalResults() {
        return totalResults;
    }
    
    public LocalDateTime getSearchedAt() {
        return searchedAt;
    }
    
    // Setter 메서드들
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setItems(List<MusicItemDto> items) {
        this.items = items != null ? items : new ArrayList<>();
    }
    
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    
    public void setSearchedAt(LocalDateTime searchedAt) {
        this.searchedAt = searchedAt;
    }
    
    // Builder 패턴 구현
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private boolean success;
        private String message;
        private List<MusicItemDto> items = new ArrayList<>();
        private int totalResults;
        private LocalDateTime searchedAt;
        
        public Builder success(boolean success) {
            this.success = success;
            return this;
        }
        
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        
        public Builder items(List<MusicItemDto> items) {
            this.items = items != null ? items : new ArrayList<>();
            return this;
        }
        
        public Builder totalResults(int totalResults) {
            this.totalResults = totalResults;
            return this;
        }
        
        public Builder searchedAt(LocalDateTime searchedAt) {
            this.searchedAt = searchedAt;
            return this;
        }
        
        public MusicSearchResponseDto build() {
            return new MusicSearchResponseDto(success, message, items, totalResults, searchedAt);
        }
    }
}