package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.team4.mywebapp.dto.GuestBookDto;
import com.team4.mywebapp.service.GuestBookService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GuestBookRestController {

    @Autowired
    private GuestBookService guestbookService;

    /**
     * 방명록 목록을 조회하는 API
     * @param guestBookMiniHomeId 미니홈피 ID
     * @return 방명록 목록 및 200 OK 응답
     */
    @GetMapping("/guestbook-list")
    public ResponseEntity<List<GuestBookDto>> getGuestbookList(@RequestParam int guestBookMiniHomeId) {
        List<GuestBookDto> guestbookList = guestbookService.getGuestbookList(guestBookMiniHomeId);
        return ResponseEntity.ok(guestbookList);
    }

    /**
     * 방명록을 삭제하는 API
     * @param guestbookDto 삭제할 방명록 ID를 담은 객체
     * @return 삭제 성공 시 200 OK, 실패 시 500 Internal Server Error 응답
     */
    @PostMapping("/guestbook-delete")
    public ResponseEntity<String> deleteGuestbook(@RequestBody GuestBookDto guestbookDto) {
        boolean isSuccess = guestbookService.deleteGuestbook(guestbookDto.getGuestBookId());
        if (isSuccess) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(500).body("fail");
        }
    }

    /**
     * 방명록을 추가하는 API
     * @param guestbookDto 추가할 방명록 정보 객체
     * @return 추가 성공 시 200 OK, 실패 시 500 Internal Server Error 응답
     */
    @PostMapping("/guestbook-insert")
    public ResponseEntity<String> insertGuestbook(@RequestBody GuestBookDto guestbookDto) {
        boolean isSuccess = guestbookService.insertGuestbook(guestbookDto);
        if (isSuccess) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(500).body("fail");
        }
    }
}