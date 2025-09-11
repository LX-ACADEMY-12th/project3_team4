package com.team4.mywebapp.dto;
import java.time.LocalDateTime;
// 롬복은 컴파일 과정에서 자동으로 개발자가 원하는 메소드를 생성/주입 방식으로 동작하는 라이브러리
import lombok.Data;


public class UserDto {
	private Long userId;
	
	private String password;
	private String nickname;
	private String gender;
	private String profileImage;
	private String statusMessage;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
