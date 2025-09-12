package com.team4.mywebapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.team4.mywebapp.dto.GuestBookDTO;


@Mapper
public interface GuestBookMapper {

	// 추가 -> 추가니까 int 로 바뀐행..
	int insertGuestBook(GuestBookDTO guestbookDto); // 사람을 넣어서.. 누가 추가하는지
	
	// 방명록 리스트 불러오는 것
	List<GuestBookDTO> getGuestBookList(int guestBookMiniHomeId);
	
	// 삭제 ? int로 해야하납..
	int deleteGuestBook(Long guestBookId);
}
