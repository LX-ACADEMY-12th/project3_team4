package com.team4.mywebapp.dto;

import java.time.LocalDateTime;

public class GuestBookDto {

	private Long guestBookId;
	private String nickname;
	private String guestbookContent;
	private LocalDateTime writtenAt; // 작성일시
	private int guestBookMiniHomeId;
	private int guestbookWriterId;
	private String profilePhotoPath;
	
	//추가한거임
	private String miniHomeOwnerLoginId;
	
	public String getProfilePhotoPath() {
		return profilePhotoPath;
	}
	public void setProfilePhotoPath(String profilePhotoPath) {
		this.profilePhotoPath = profilePhotoPath;
	}
	public Long getGuestBookId() {
		return guestBookId;
	}
	public void setGuestBookId(Long guestBookId) {
		this.guestBookId = guestBookId;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getGuestbookContent() {
		return guestbookContent;
	}
	public void setGuestbookContent(String guestbookContent) {
		this.guestbookContent = guestbookContent;
	}
	public LocalDateTime getWrittenAt() {
		return writtenAt;
	}
	public void setWrittenAt(LocalDateTime writtenAt) {
		this.writtenAt = writtenAt;
	}
	public int getGuestBookMiniHomeId() {
		return guestBookMiniHomeId;
	}
	public void setGuestBookMiniHomeId(int guestBookMiniHomeId) {
		this.guestBookMiniHomeId = guestBookMiniHomeId;
	}
	
	public int getGuestbookWriterId() {
		return guestbookWriterId;
	}
	public void setGuestbookWriterId(int guestbookWriterId) {
		this.guestbookWriterId = guestbookWriterId;
	}
	public String getMiniHomeOwnerLoginId() {
		return miniHomeOwnerLoginId;
	}
	public void setMiniHomeOwnerLoginId(String miniHomeOwnerLoginId) {
		this.miniHomeOwnerLoginId = miniHomeOwnerLoginId;
	}
	
	
	
}