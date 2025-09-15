package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team4.mywebapp.dto.UserModifyDto;
import com.team4.mywebapp.service.UserModifyService;

import java.io.File;        
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserModifyController {
	
	@Autowired
	private UserModifyService usermodifyService;
	
	/**
     * 사용자 정보 조회하는 api
     * @param  minihome_id
     * @return 사용자 정보 Dto
     */
	@GetMapping("/user-profile")
	public ResponseEntity<UserModifyDto> getUserProfile(@RequestParam int minihomeId) {
		UserModifyDto userProfile = usermodifyService.getUserProfile(minihomeId);
		return ResponseEntity.ok(userProfile);		
	}
	
	/**
     * 사용자 정보 업데이트
     * @param 수정할 사용자 정보 Dto
     * @return 업데이트 된 행의 개수
     */
	@PostMapping("/user-update")
	public ResponseEntity<String> updateUser(@RequestBody UserModifyDto usermodifyDto) {
		int updateRows = usermodifyService.updateUser(usermodifyDto);
		if (updateRows > 0) {
			return ResponseEntity.ok("success");
		} else {
			return ResponseEntity.status(500).body("fail");	
		}
		
 }
	
	// 새로 추가: 미니홈피 정보 조회용 API
    /**
     * HTTP Get 요청을 받아 loginId를 파라미터로 연결해 주는 과정을 수행하는 API입니다.
     * 
     * @param 로그인한 사용자의 loginId
     * @return 성공 시 200 OK, 실패 시 404 body
     */
    @GetMapping("/minihome/{loginId}")  // RESTful 방식: URL 경로에 ID 포함
    public ResponseEntity<?> getMinihomeInfo(@PathVariable String loginId) {
        try {
            // 로그인 ID로 사용자의 미니홈피 정보를 조회
            System.out.println("미니홈피 정보 요청받은 loginId : " + loginId);
            
            // 서비스 계층에서 미니홈피 정보를 가져오는 메서드 호출
            UserModifyDto minihomeInfo = usermodifyService.getUserProfileInLoginId(loginId);
            
            if (minihomeInfo != null) {
                // 미니홈피 정보가 존재하는 경우
                return ResponseEntity.ok(minihomeInfo);
            } else {
                // 미니홈피 정보가 없는 경우
                return ResponseEntity.status(404).body("해당 사용자의 미니홈피를 찾을 수 없습니다.");
            }
            
        } catch (Exception e) {
            // 서버 오류 발생 시
            System.err.println("미니홈피 정보 조회 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다.");
        }
    }
   
	/**
     * 파일 업로드 전용 API
     * @param 
     * @return 
     */
    @PostMapping("/user-profile-image")
    public ResponseEntity<String> updateProfileImage(@RequestParam("minihomeId") int minihomeId, @RequestParam("profileImageFile") MultipartFile profileImageFile
    ) {
        try {// 예외 처리 시작
            // 1. 파일을 서버에 저장하고 저장 경로를 받음
            String savedPath = saveProfileImage(profileImageFile, String.valueOf(minihomeId));
            
            // 2. DB 업데이트를 위한 DTO 객체 생성
            UserModifyDto dto = new UserModifyDto();
            dto.setMinihomeId(minihomeId); // 어떤 미니홈피인지
            dto.setProfilePhotoPath(savedPath); // 저장된 파일 경로를 DTO에
            
            // 3. Service를 통해 DB의 프로필 경로 업데이트
            int updateRows = usermodifyService.updateUserProfilePhoto(dto);
            
            // 4. 업데이트 성공 여부 확인
            if (updateRows > 0) {
                return ResponseEntity.ok(savedPath);
            } else {
                return ResponseEntity.status(500).body("fail");
            }
            
        } catch (Exception e) {
            System.err.println("프로필 이미지 업로드 오류: " + e.getMessage());
            return ResponseEntity.status(500).body("fail");
        }
    }
    
    // 실제 파일을 서버에 저장하는 private 메소드   
    private String saveProfileImage(MultipartFile file, String string) {
        try {
            String uploadDir = "C:/Users/parkb/CYWORLD/frontend_vue/src/assets/images/userProfile/";
            
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                originalFileName = "upload.jpg";
            }
            
            String fileName = string + "_" + System.currentTimeMillis() + "_" + originalFileName;
            File targetFile = new File(uploadDir + fileName);
            
            // 디렉토리 생성 및 파일 저장
            targetFile.getParentFile().mkdirs();
            file.transferTo(targetFile);
            
            return "/src/assets/images/userProfile/" + fileName;
            
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + e.getMessage(), e);
        }
    }
    
	
}
