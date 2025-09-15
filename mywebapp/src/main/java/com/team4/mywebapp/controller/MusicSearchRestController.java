package com.team4.mywebapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.team4.mywebapp.dto.MusicSearchResponseDto;
import com.team4.mywebapp.service.MusicSearchService;

/**
 * 음악 검색 관련 REST API 컨트롤러
 * YouTube Data API v3를 통해 음악을 검색하는 기능 제공
 */
@RestController
@RequestMapping("/api/music")
@CrossOrigin(origins = "*") // CORS 설정
public class MusicSearchRestController {

    private static final Logger log = LoggerFactory.getLogger(MusicSearchRestController.class);
    
    private final MusicSearchService musicSearchService;

    // 생성자 주입
    @Autowired
    public MusicSearchRestController(MusicSearchService musicSearchService) {
        this.musicSearchService = musicSearchService;
    }

    /**
     * 유튜브에서 음악을 검색하는 API
     * 
     * @param query 검색할 음악 제목 또는 아티스트명
     * @param maxResults 최대 검색 결과 수 (기본값: 10, 최대: 50)
     * @return 검색 결과가 포함된 응답 DTO
     * 
     * 사용 예시:
     * GET /api/music/search?query=아이유 좋은날&maxResults=5
     */
    @GetMapping("/search")
    public ResponseEntity<MusicSearchResponseDto> searchMusic(
            @RequestParam("query") String query,
            @RequestParam(value = "maxResults", defaultValue = "10") int maxResults) {
        
        try {
            log.info("음악 검색 요청 - 검색어: {}, 최대 결과 수: {}", query, maxResults);
            
            // 입력값 검증
            if (query == null || query.trim().isEmpty()) {
                MusicSearchResponseDto errorResponse = MusicSearchResponseDto.builder()
                        .success(false)
                        .message("검색어를 입력해주세요.")
                        .build();
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // maxResults 범위 제한 (1~50)
            if (maxResults < 1 || maxResults > 50) {
                maxResults = 10; // 기본값으로 설정
            }
            
            // 음악 검색 서비스 호출
            MusicSearchResponseDto response = musicSearchService.searchMusic(query.trim(), maxResults);
            
            log.info("음악 검색 완료 - 검색어: {}, 결과 수: {}", query, response.getTotalResults());
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("잘못된 요청 파라미터: {}", e.getMessage());
            
            MusicSearchResponseDto errorResponse = MusicSearchResponseDto.builder()
                    .success(false)
                    .message("잘못된 요청입니다: " + e.getMessage())
                    .build();
                    
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            log.error("음악 검색 중 예상치 못한 오류 발생: {}", e.getMessage(), e);
            
            MusicSearchResponseDto errorResponse = MusicSearchResponseDto.builder()
                    .success(false)
                    .message("음악 검색 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
                    .build();
                    
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * API 상태 확인용 헬스 체크 엔드포인트
     * 
     * @return 서비스 상태 정보
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        log.debug("음악 검색 API 헬스 체크 요청");
        return ResponseEntity.ok("Music Search API is running");
    }
}