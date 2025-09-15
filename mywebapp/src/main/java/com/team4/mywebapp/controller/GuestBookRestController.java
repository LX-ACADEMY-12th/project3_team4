package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.team4.mywebapp.dto.GuestBookDto;
import com.team4.mywebapp.service.GuestBookService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Vue 개발 서버 주소
public class GuestBookRestController {

    @Autowired
    private GuestBookService guestbookService;

    /**
     * 방명록 목록을 조회하는 API
     * @param guestBookMiniHomeId 미니홈피 ID
     * @return 방명록 목록 및 200 OK 응답
     */
    @GetMapping("/guestbook-list")
    public ResponseEntity<?> getGuestbookList(@RequestParam("miniHomeOwnerLoginId") String miniHomeOwnerLoginId) {
    	try {
            List<GuestBookDto> guestbooks = guestbookService.getGuestbookList(miniHomeOwnerLoginId);
            
            // 디버깅용 로그
            System.out.println("방명록 개수: " + guestbooks.size());
            for (GuestBookDto dto : guestbooks) {
                System.out.println("profilePhotoPath: " + dto.getProfilePhotoPath());
            }
            
            return ResponseEntity.ok(guestbooks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("방명록을 불러오는데 실패했습니다.");
        }
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
        try {
            boolean isSuccess = guestbookService.insertGuestbook(guestbookDto);
            
            if (isSuccess) {
                // 방명록 추가 성공
                System.out.println("방명록 추가 성공: " + guestbookDto.toString());
                return ResponseEntity.ok("success");
            } else {
                // 서비스에서 false를 반환한 경우 (논리적 실패)
                System.out.println("방명록 추가 실패: 서비스 로직에서 실패");
                return ResponseEntity.status(500).body("fail");
            }
        } catch (Exception e) {
            // 예상치 못한 예외가 발생한 경우 (예: DB 연결 오류, SQL 오류)
            System.err.println("방명록 추가 중 예상치 못한 오류 발생: " + e.getMessage());
            e.printStackTrace(); // 상세한 스택 트레이스를 콘솔에 출력
            return ResponseEntity.status(500).body("fail");
        }
    }
}