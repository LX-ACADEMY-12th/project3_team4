package com.team4.mywebapp.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.mywebapp.dto.MusicItemDto;
import com.team4.mywebapp.dto.MusicSearchResponseDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 음악 검색 서비스
 * YouTube Data API v3를 사용하여 음악을 검색하고 결과를 가공하는 비즈니스 로직 처리
 */
@Service
public class MusicSearchService {

    private static final Logger log = LoggerFactory.getLogger(MusicSearchService.class);
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    // YouTube Data API v3 검색 엔드포인트
    private static final String YOUTUBE_SEARCH_URL = "https://www.googleapis.com/youtube/v3/search";
    
    // 음악 카테고리 ID (YouTube에서 정의한 음악 카테고리)
    private static final String MUSIC_CATEGORY_ID = "10";

    // application.properties에서 주입받는 YouTube API 키
    @Value("${youtube.api.key}")
    private String youtubeApiKey;

    // 생성자 주입
    @Autowired
    public MusicSearchService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 유튜브에서 음악을 검색합니다
     * 
     * @param query 검색할 음악 제목 또는 아티스트명
     * @param maxResults 최대 검색 결과 수 (1-50 사이)
     * @return 검색 결과를 담은 응답 DTO
     * @throws IllegalArgumentException 잘못된 입력 파라미터
     * @throws RuntimeException YouTube API 호출 실패
     */
    public MusicSearchResponseDto searchMusic(String query, int maxResults) {
        try {
            log.debug("YouTube 음악 검색 시작 - 검색어: {}, 최대 결과: {}", query, maxResults);
            
            // 입력값 검증
            validateSearchParameters(query, maxResults);
            
            // YouTube API URL 생성
            String apiUrl = buildYouTubeApiUrl(query, maxResults);
            log.debug("YouTube API 요청 URL: {}", apiUrl.replaceAll("key=[^&]+", "key=***"));
            
            // YouTube API 호출
            String jsonResponse = callYouTubeApi(apiUrl);
            
            // JSON 응답 파싱하여 음악 아이템 목록 생성
            List<MusicItemDto> musicItems = parseYouTubeResponse(jsonResponse);
            
            // 성공 응답 생성
            MusicSearchResponseDto response = MusicSearchResponseDto.builder()
                    .success(true)
                    .message("검색이 완료되었습니다")
                    .items(musicItems)
                    .totalResults(musicItems.size())
                    .searchedAt(LocalDateTime.now())
                    .build();
                    
            log.info("음악 검색 성공 - 검색어: {}, 결과 수: {}", query, musicItems.size());
            return response;

        } catch (IllegalArgumentException e) {
            log.warn("잘못된 검색 파라미터: {}", e.getMessage());
            throw e; // 컨트롤러에서 처리하도록 재던짐
            
        } catch (HttpClientErrorException e) {
            log.error("YouTube API 클라이언트 오류 - 상태코드: {}, 메시지: {}", e.getStatusCode(), e.getMessage());
            
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new RuntimeException("YouTube API 키가 유효하지 않거나 할당량이 초과되었습니다.");
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("잘못된 YouTube API 요청입니다.");
            } else {
                throw new RuntimeException("YouTube API 요청 중 오류가 발생했습니다: " + e.getMessage());
            }
            
        } catch (HttpServerErrorException e) {
            log.error("YouTube API 서버 오류 - 상태코드: {}", e.getStatusCode());
            throw new RuntimeException("YouTube 서버에 일시적인 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
            
        } catch (Exception e) {
            log.error("음악 검색 중 예상치 못한 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("음악 검색에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 검색 파라미터 유효성 검증
     * 
     * @param query 검색어
     * @param maxResults 최대 결과 수
     * @throws IllegalArgumentException 잘못된 파라미터인 경우
     */
    private void validateSearchParameters(String query, int maxResults) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 필수입니다.");
        }
        
        if (query.trim().length() > 100) {
            throw new IllegalArgumentException("검색어는 100자를 초과할 수 없습니다.");
        }
        
        if (maxResults < 1 || maxResults > 50) {
            throw new IllegalArgumentException("검색 결과 수는 1-50 사이여야 합니다.");
        }
    }

    /**
     * YouTube API 호출을 위한 URL 생성
     * 
     * @param query 검색어
     * @param maxResults 최대 결과 수
     * @return 완성된 API URL
     */
    private String buildYouTubeApiUrl(String query, int maxResults) {
        return UriComponentsBuilder.fromHttpUrl(YOUTUBE_SEARCH_URL)
                .queryParam("part", "snippet")                    // 가져올 데이터 부분
                .queryParam("q", query + " music")               // 검색어 + "music" 키워드 추가
                .queryParam("type", "video")                     // 비디오만 검색
                .queryParam("videoCategoryId", MUSIC_CATEGORY_ID) // 음악 카테고리로 제한
                .queryParam("maxResults", maxResults)            // 최대 결과 수
                .queryParam("order", "relevance")               // 관련도 순으로 정렬
                .queryParam("safeSearch", "moderate")           // 중간 수준의 안전 검색
                .queryParam("key", youtubeApiKey)               // API 키
                .build()
                .toUriString();
    }

    /**
     * YouTube API 호출
     * 
     * @param apiUrl API 요청 URL
     * @return JSON 응답 문자열
     * @throws RuntimeException API 호출 실패시
     */
    private String callYouTubeApi(String apiUrl) {
        String response = restTemplate.getForObject(apiUrl, String.class);
        
        if (response == null || response.trim().isEmpty()) {
            throw new RuntimeException("YouTube API에서 빈 응답을 받았습니다.");
        }
        
        return response;
    }

    /**
     * YouTube API JSON 응답을 파싱하여 음악 아이템 리스트로 변환
     * 
     * @param jsonResponse YouTube API JSON 응답
     * @return 파싱된 음악 아이템 리스트
     * @throws RuntimeException JSON 파싱 실패시
     */
    private List<MusicItemDto> parseYouTubeResponse(String jsonResponse) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            
            // 오류 응답 체크
            if (rootNode.has("error")) {
                JsonNode errorNode = rootNode.get("error");
                String errorMessage = errorNode.get("message").asText();
                throw new RuntimeException("YouTube API 오류: " + errorMessage);
            }
            
            JsonNode itemsNode = rootNode.get("items");
            List<MusicItemDto> musicItems = new ArrayList<>();

            if (itemsNode != null && itemsNode.isArray()) {
                for (JsonNode item : itemsNode) {
                    try {
                        MusicItemDto musicItem = parseSingleMusicItem(item);
                        if (musicItem != null) {
                            musicItems.add(musicItem);
                        }
                    } catch (Exception e) {
                        log.warn("개별 음악 아이템 파싱 중 오류 (건너뜀): {}", e.getMessage());
                        // 개별 아이템 오류는 무시하고 계속 진행
                    }
                }
            }

            log.debug("총 {}개의 음악 아이템 파싱 완료", musicItems.size());
            return musicItems;

        } catch (Exception e) {
            log.error("YouTube API 응답 파싱 실패: {}", e.getMessage(), e);
            throw new RuntimeException("검색 결과 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * 단일 음악 아이템을 파싱
     * 
     * @param item YouTube API 응답의 개별 아이템
     * @return 파싱된 음악 아이템 DTO, 파싱 실패시 null
     */
    private MusicItemDto parseSingleMusicItem(JsonNode item) {
        JsonNode snippet = item.get("snippet");
        JsonNode id = item.get("id");

        if (snippet == null || id == null) {
            log.warn("필수 필드가 없는 아이템 건너뜀");
            return null;
        }

        // 기본 정보 추출
        String videoId = id.get("videoId").asText();
        String title = snippet.get("title").asText();
        String channelTitle = snippet.get("channelTitle").asText();
        String description = snippet.has("description") ? snippet.get("description").asText() : "";
        String publishedAt = snippet.has("publishedAt") ? snippet.get("publishedAt").asText() : "";

        // 썸네일 URL 추출
        String thumbnailUrl = extractThumbnailUrl(snippet);

        // 음악이 아닌 콘텐츠 필터링
        if (isNonMusicContent(title, description)) {
            log.debug("음악이 아닌 콘텐츠로 판단되어 제외: {}", title);
            return null;
        }

        return MusicItemDto.builder()
                .videoId(videoId)
                .title(cleanTitle(title))
                .channelTitle(channelTitle)
                .description(description.length() > 200 ? description.substring(0, 200) + "..." : description)
                .thumbnail(thumbnailUrl)
                .publishedAt(publishedAt)
                .build();
    }

    /**
     * 썸네일 URL 추출 (고화질 우선)
     */
    private String extractThumbnailUrl(JsonNode snippet) {
        JsonNode thumbnails = snippet.get("thumbnails");
        if (thumbnails == null) {
            return "";
        }

        String[] qualities = {"high", "medium", "default"};
        
        for (String quality : qualities) {
            JsonNode thumbnail = thumbnails.get(quality);
            if (thumbnail != null && thumbnail.has("url")) {
                return thumbnail.get("url").asText();
            }
        }

        return "";
    }

    /**
     * 음악이 아닌 콘텐츠인지 판단
     */
    private boolean isNonMusicContent(String title, String description) {
        String lowerTitle = title.toLowerCase();
        
        String[] nonMusicKeywords = {
            "vlog", "review", "tutorial", "gameplay", "interview", 
            "news", "podcast", "lecture", "documentary"
        };
        
        for (String keyword : nonMusicKeywords) {
            if (lowerTitle.contains(keyword)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * 제목 정리 (HTML 엔티티 디코딩)
     */
    private String cleanTitle(String title) {
        if (title == null) {
            return "";
        }
        
        return title.replace("&amp;", "&")
                   .replace("&lt;", "<")
                   .replace("&gt;", ">")
                   .replace("&quot;", "\"")
                   .replace("&#39;", "'")
                   .trim();
    }
}