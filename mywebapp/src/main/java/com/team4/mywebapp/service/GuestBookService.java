package com.team4.mywebapp.service;

import com.team4.mywebapp.dto.GuestBookDto;
import com.team4.mywebapp.mapper.GuestBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GuestBookService {

	@Autowired
    private GuestBookMapper guestbookMapper;
    
    /**
     * 특정 미니홈피의 방명록 목록을 가져옵니다.
     * 
     * @param miniHomeOwnerLoginId 미니홈피 주인의 로그인 ID
     * @return 방명록 목록
     */
    public List<GuestBookDto> getGuestbookList(String miniHomeOwnerLoginId) {
        return guestbookMapper.getGuestbookList(miniHomeOwnerLoginId);
    }

	/**
	 * 방명록을 삭제합니다.
	 * 
	 * @param guestBookId 삭제할 방명록 ID
	 * @return 삭제 성공 시 true, 실패 시 false
	 */
	public boolean deleteGuestbook(Long guestBookId) {
		return guestbookMapper.deleteGuestbook(guestBookId) > 0;
	}

	/**
	 * 방명록을 추가합니다.
	 * 
	 * @param guestbookDto 추가할 방명록 정보
	 * @return 추가 성공 시 true, 실패 시 false
	 */
	public boolean insertGuestbook(GuestBookDto guestbookDto) {
		return guestbookMapper.insertGuestbook(guestbookDto) > 0;
	}
}