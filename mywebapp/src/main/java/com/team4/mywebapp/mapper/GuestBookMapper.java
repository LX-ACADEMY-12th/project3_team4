package com.team4.mywebapp.mapper;

import com.team4.mywebapp.dto.GuestBookDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface GuestBookMapper {

    // 특정 미니홈피의 모든 방명록 목록을 조회
    List<GuestBookDto> getGuestbookList(@Param("miniHomeOwnerLoginId") String miniHomeOwnerLoginId);

    // 방명록 삭제
    int deleteGuestbook(@Param("guestBookId") Long guestBookId);

    // 방명록 추가
    int insertGuestbook(GuestBookDto guestbook);
    
    // loginId(String) -> minihome_id 리턴하는
    Integer findMinihomeIdByLoginId(@Param("loginId") String loginId);
}