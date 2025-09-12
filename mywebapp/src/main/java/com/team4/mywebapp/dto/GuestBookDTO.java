package com.team4.mywebapp.dto;

import java.time.LocalDateTime;

public class GuestBookDTO {

	private Long guestBookId;
	private String nickname;
	private String guestBookContent;
	private LocalDateTime writtenAt; // 작성일시
	private int guestBookMiniHomeId;
	// private int guestBookReplyId;
	private int guestBookWriterId;
	
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
	
	public String getGuestBookContent() {
		return guestBookContent;
	}
	public void setGuestBookContent(String guestBookContent) {
		this.guestBookContent = guestBookContent;
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
	
	/*
	public int getGuestBookReplyId() {
		return guestBookReplyId;
	}
	public void setGuestBookReplyId(int guestBookReplyId) {
		this.guestBookReplyId = guestBookReplyId;
	}
	*/
	public int getGuestBookWriterId() {
		return guestBookWriterId;
	}
	public void setGuestBookWriterId(int guestBookWriterId) {
		this.guestBookWriterId = guestBookWriterId;
	}
	
}
