package com.team4.mywebapp.controller;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team4.mywebapp.dto.GuestBookDTO;
import com.team4.mywebapp.service.GuestBookService;

@RestController
@RequestMapping("/api")  // ← API 경로
@CrossOrigin(origins = "http://localhost:5173")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestBookService;
	
	// 근데 우리는 vue를 쓰니까 return을 페이지로 렌딩할필요 ㄴㄴ인듯..
	
	// 방명록 리스트 출력 처리
	
	@GetMapping("/guestbook-list")
	public ResponseEntity<List<GuestBookDTO>> list(@RequestParam int guestBookMiniHomeId) {
		
		List<GuestBookDTO> list = guestBookService.getGuestBookList(guestBookMiniHomeId);
		return ResponseEntity.ok(list);
		// 클래스말고 소문자 인스턴스 변수로 접근!!
	}
	
	// 방명록 추가하는!!
	@PostMapping("/guestbook-insert")
	public ResponseEntity<Integer> insert(@RequestBody GuestBookDTO guestBookDto) {
		
		int check = guestBookService.insertGuestBook(guestBookDto);
		//return guestBookService.insertGuestBook(guestBookDto);
		return ResponseEntity.ok(check);
	}
	
	// 방명록을 삭제하는
	@PostMapping("/guestbook-delete")
	public ResponseEntity<Integer> delete(@RequestBody GuestBookDTO guestBookDto) {
		
		return ResponseEntity.ok(guestBookService.deleteGuestBook(guestBookDto.getGuestBookId()));
	
	}
}
