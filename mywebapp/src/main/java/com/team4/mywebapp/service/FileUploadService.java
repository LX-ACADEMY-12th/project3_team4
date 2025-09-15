package com.team4.mywebapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
    
	@Value("${file.upload.path:uploads/}")
    private String uploadPath;

	public String saveProfileImage(MultipartFile file, String loginId) throws IOException {
	    // static 폴더에 저장
	    Path uploadDir = Paths.get("src/main/resources/static/uploads", "profiles");
	    
	    if (!Files.exists(uploadDir)) {
	        Files.createDirectories(uploadDir);
	    }

	    String originalFilename = file.getOriginalFilename();
	    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
	    String filename = loginId + "_" + System.currentTimeMillis() + extension;

	    Path filePath = uploadDir.resolve(filename);
	    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	    return "/uploads/profiles/" + filename;
	}
}