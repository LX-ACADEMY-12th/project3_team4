package com.team4.mywebapp.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MinihompyDto {
    private Long minihompyId;
    private Long userId;
    private String title;
    private String theme;
    private String backgroundColor;
    private String minimiCharacter;
    private String minimiPosition;
    private String bgmUrl;
    private String bgmTitle;
    private String bgmArtist;
    private Integer todayVisitors;
    private Integer totalVisitors;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
