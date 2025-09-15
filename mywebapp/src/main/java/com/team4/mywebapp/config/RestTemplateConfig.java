package com.team4.mywebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 설정 클래스
 * YouTube API 호출을 위한 HTTP 클라이언트 빈 등록
 */
@Configuration
public class RestTemplateConfig {

    /**
     * RestTemplate 빈 등록
     * MusicSearchService에서 YouTube API 호출할 때 사용
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}