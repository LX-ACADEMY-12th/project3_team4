package com.team4.mywebapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.team4.mywebapp.dto.GuestBookDTO;
import com.team4.mywebapp.mapper.GuestBookMapper;

@Service
public class GuestBookService {
	
	@Autowired
	private GuestBookMapper guestBookMapper;
	
	public int insertGuestBook(GuestBookDTO guestBookDto) {
		return guestBookMapper.insertGuestBook(guestBookDto);
	}
	
	public List<GuestBookDTO> getGuestBookList(int guestBookMiniHomeId) {
		return guestBookMapper.getGuestBookList(guestBookMiniHomeId);
	}
	
	public int deleteGuestBook(Long guestBookId) {
		return guestBookMapper.deleteGuestBook(guestBookId);
	}
}
