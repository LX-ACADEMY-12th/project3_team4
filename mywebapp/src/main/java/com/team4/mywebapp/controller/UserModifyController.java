package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	/**
     * 파일 업로드 전용 API
     * @param 
     * @return 
     */
	
		
	// 파일 업로드 전용 API
    @PostMapping("/user-profile-image")
    public ResponseEntity<String> updateProfileImage(@RequestParam("minihomeId") int minihomeId, @RequestParam("profileImageFile") MultipartFile profileImageFile
    ) {
        try {// 예외 처리 시작
            // 1. 파일을 서버에 저장하고 저장 경로를 받음
            String savedPath = saveProfileImage(profileImageFile);
            
            // 2. DB 업데이트를 위한 DTO 객체 생성
            UserModifyDto dto = new UserModifyDto();
            dto.setMinihomeId(minihomeId); // 어떤 미니홈피인지
            dto.setProfilePhotoPath(savedPath); // 저장된 파일 경로를 DTO에
            
            // 3. Service를 통해 DB의 프로필 경로 업데이트
            int updateRows = usermodifyService.updateUser(dto);
            
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
    private String saveProfileImage(MultipartFile file) {
        try {
            String uploadDir = "uploads/profile/";
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + fileName;
            
            new File(uploadDir).mkdirs();  // 폴더 생성 (이미 있으면 무시)
            file.transferTo(new File(filePath));  // 파일 저장
            
            return filePath;
            
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
	
}
