package com.team4.mywebapp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.MiniHomeDto;
import com.team4.mywebapp.dto.MiniHomeUpdateDto;
import com.team4.mywebapp.dto.MiniHomeUpdateRequestDto;
import com.team4.mywebapp.dto.UpdateMiniHomeResponseDto;
import com.team4.mywebapp.dto.UserUpdateDto;
import com.team4.mywebapp.mapper.MiniHomeMapper;
import com.team4.mywebapp.mapper.UserMapper;

@Service // 이 클래스가 서비스 계층의 컴포넌트임을 나타냄.
public class MiniHomeService {
	
    @Autowired
    private FileUploadService fileUploadService;

	@Autowired // Spirng이 MiniHomeMapper의 인스턴스를 자동으로 주입
	private MiniHomeMapper miniHomeMapper;
	
	@Autowired
	private UserMapper userMapper;

	// 미니홈피 정보를 조회하는 비즈니스 메서드
	public MiniHomeDto getMiniHomeByUserId(String userId) {
	    try {
	        System.out.println("=== 미니홈피 조회 시작 ===");
	        System.out.println("조회할 사용자 ID: " + userId);
	        
	        // 매퍼를 호출하여 DB에서 데이터를 가져옵니다.
	        MiniHomeDto miniHomeInfo = miniHomeMapper.getMiniHomeByUserId(userId);

	        // 조회된 데이터가 null 인지 확인하고, 필요한 경우 예외를 처리하거니 기본값을 반환
	        if (miniHomeInfo == null) {
	            System.out.println("조회 결과: null - 미니홈피 정보 없음");
	            throw new IllegalArgumentException("해당 사용자의 미니홈피 정보를 찾을 수 없습니다.");
	        }

	        // 조회된 데이터 로그 출력
	        System.out.println("조회 결과:");
	        System.out.println("  - userId: " + miniHomeInfo.getUserId());
	        System.out.println("  - nickname: " + miniHomeInfo.getNickname());
	        System.out.println("  - profileImagePath: " + miniHomeInfo.getProfileImagePath());
	        System.out.println("  - statusMessage: " + miniHomeInfo.getStatusMessage());
	        System.out.println("  - todayMood: " + miniHomeInfo.getTodayMood());
	        System.out.println("  - youtubeVideoId: " + miniHomeInfo.getYoutubeVideoId());
	        System.out.println("  - backgroundColor: " + miniHomeInfo.getBackgroundColor());
	        System.out.println("=== 미니홈피 조회 완료 ===");

	        return miniHomeInfo;
	        
	    } catch (Exception e) {
	        // DB 연결 실패 또는 기타 예외 발생 시 로그 기록
	        System.err.println("DB 연결 또는 쿼리 실행에 실패했습니다. userId: " + userId);
	        e.printStackTrace(); // 상세한 스택 트레이스 출력
	        throw new RuntimeException("데이터베이스 연결 오류", e); // 더 높은 계층으로 예외 재전달
	    }
	}

	// 미니홈피 정보 갱신하는 비즈니스 메서드
	public UpdateMiniHomeResponseDto updateMiniHome(MiniHomeUpdateRequestDto requestDto) {
        try {
        	String profileImagePath = null;
        	
        	System.out.println("파일 업로드 처리 시작");
        	System.out.println("파일 객체: " + requestDto.getProfileImageFile());
        	
            // 프로필 이미지 파일이 있으면 저장
            if (requestDto.getProfileImageFile() != null && !requestDto.getProfileImageFile().isEmpty()) {
                System.out.println("파일 업로드 조건 충족 - FileUploadService 호출");
                profileImagePath = fileUploadService.saveProfileImage(
                    requestDto.getProfileImageFile(), 
                    requestDto.getMiniHomeOwnerLoginId()
                );
            } else {
                System.out.println("파일 업로드 조건 불충족 - 파일이 null이거나 empty");
            }
            
            _updateUserInfo(requestDto, profileImagePath); // 파라미터 추가
            _updateMiniHomeInfo(requestDto);
            
            MiniHomeDto updatedMiniHome = miniHomeMapper.getMiniHomeByUserId(requestDto.getMiniHomeOwnerLoginId());
            
            UpdateMiniHomeResponseDto response = UpdateMiniHomeResponseDto.builder()
                .success(true)
                .message("미니홈피 정보가 성공적으로 업데이트되었습니다.")
                .profileImageUrl(profileImagePath) // 새 이미지 경로 반환
                .updatedMiniHomeInfo(updatedMiniHome)
                .build();
            
            return response;

        } catch (Exception e) {
            UpdateMiniHomeResponseDto errorResponse = UpdateMiniHomeResponseDto.builder()
                .success(false)
                .message("미니홈피 업데이트 중 오류가 발생했습니다: " + e.getMessage())
                .build();
            
            throw new RuntimeException("미니홈피 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    private void _updateUserInfo(MiniHomeUpdateRequestDto requestDto, String profileImagePath) {

        System.out.println("=== 사용자 정보 업데이트 시작 ===");
        System.out.println("업데이트할 사용자 ID: " + requestDto.getUserId());
        System.out.println("프로필 이미지 파일 null 여부: " + (requestDto.getProfileImageFile() == null));
        System.out.println("프로필 이미지 파일 empty 여부: " + (requestDto.getProfileImageFile() != null ? requestDto.getProfileImageFile().isEmpty() : "null이므로 확인불가"));
        System.out.println("새 프로필 이미지 경로: " + profileImagePath);
        System.out.println("상태 메시지: '" + requestDto.getStatusMessage() + "'");
        System.out.println("오늘 기분: '" + requestDto.getTodayMood() + "'");
        System.out.println("생년월일: '" + requestDto.getBirthDate() + "'");
        System.out.println("성별: '" + requestDto.getGender() + "'");
        System.out.println("지역: '" + requestDto.getRegion() + "'");
        System.out.println("취미: '" + requestDto.getHobby() + "'");
           
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .userId(requestDto.getUserId())
            .profilePhotoPath(profileImagePath) // 파라미터로 받은 값 사용
            .statusMessage(requestDto.getStatusMessage()) // 추가
            .todayMood(requestDto.getTodayMood()) // 추가
            .birthDate(requestDto.getBirthDate() != null ? requestDto.getBirthDate().toString() : null)
            .gender(requestDto.getGender())
            .region(requestDto.getRegion())
            .hobby(requestDto.getHobby())
            .build();
            
        userMapper.updateUserInfo(userUpdateDto);
    }

    private void _updateMiniHomeInfo(MiniHomeUpdateRequestDto requestDto) {
    	System.out.println("=== 미니홈피 정보 업데이트 시작 ===");
        System.out.println("YouTube Video ID: " + requestDto.getYoutubeVideoId());
        
        MiniHomeUpdateDto miniHomeUpdateDto = MiniHomeUpdateDto.builder()
            .miniHomeOwnerLoginId(requestDto.getMiniHomeOwnerLoginId())
            .youtubeVideoId(requestDto.getYoutubeVideoId())
            .backgroundColor(requestDto.getBackgroundColor())
            .appliedThemeId(requestDto.getAppliedThemeId() != null ? requestDto.getAppliedThemeId().toString() : null)
            .updatedAt(LocalDateTime.now())
            .build();

        MiniHomeDto existingMiniHome = miniHomeMapper.getMiniHomeByUserId(requestDto.getMiniHomeOwnerLoginId());
        
        if (existingMiniHome != null) {
        	System.out.println("미니홈 업데이트 시작");
        	miniHomeMapper.updateMiniHome(miniHomeUpdateDto);
        	System.out.println("BGM 업데이트 시작 - YouTube ID: " + requestDto.getYoutubeVideoId());
            miniHomeMapper.updateBgm(miniHomeUpdateDto);
            System.out.println("BGM 업데이트 완료");
        } else {
        	System.out.println("기존 미니홈을 찾을 수 없음");
        }
    }

}
