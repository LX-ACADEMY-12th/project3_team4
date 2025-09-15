package com.team4.mywebapp.dto;

import java.time.LocalDateTime;

public class MiniHomeInsertDto {

	private int userId;
	private LocalDateTime createdAt;
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime localDateTime) {
		this.createdAt = localDateTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
