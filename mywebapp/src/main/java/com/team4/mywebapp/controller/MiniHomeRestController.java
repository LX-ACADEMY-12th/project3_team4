package com.team4.mywebapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team4.mywebapp.dto.MiniHomeDto;
import com.team4.mywebapp.dto.MiniHomeUpdateRequestDto;
import com.team4.mywebapp.dto.UpdateMiniHomeResponseDto;
import com.team4.mywebapp.service.MiniHomeService;

@RestController // RESTful API를 위한 컨트롤러
@RequestMapping("/api") // 이 컨트롤러의 기본 URL 경로
@CrossOrigin(origins = "http://localhost:5173")
public class MiniHomeRestController {
	
	// Spring이 MiniHomeServcie의 인스턴스를 자동 주입 	
	@Autowired
	private MiniHomeService miniHomeService;
	
	// HTTP GET 요청을 처리하는 테스트 메서드
	@GetMapping("/test")
	public void test() {
	    System.out.println("테스트 메서드가 호출되었습니다!");
	}
	
	// HTTP GET 요청을 처리하는 메서드
    @GetMapping("/showMiniHome")
    public ResponseEntity<MiniHomeDto> getMinihome(@RequestParam("miniHomeOwnerLoginId") String userId) {
    	// userId 파라미터가 잘 받아와지는지 확인하는 로그 출력
        System.out.println("요청받은 userId: " + userId); 
        
    	try {
    		// Service 계층의 메서드를 호출하여 사용자 미니홈피 정보를 가져옵니다.
    		 MiniHomeDto miniHomedto = miniHomeService.getMiniHomeByUserId(userId);
    		
    		// 성공적으로 데이터를 가져왔을 경우, HTTP 상태 코드 200(OK)와 함께 데이터를 응답합니다.
            return ResponseEntity.ok(miniHomedto);
            
    	} catch (IllegalArgumentException e) {
            // 서비스 계층에서 예외가 발생하면, HTTP 상태 코드 404(Not Found)와 함께 오류 메시지를 응답합니다.
            // 클라이언트에게 해당 리소스(미니홈피)가 없음을 알릴 수 있습니다.
            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            // 그 외 예상치 못한 모든 예외에 대해 HTTP 상태 코드 500(Internal Server Error)을 응답합니다.
            return ResponseEntity.internalServerError().build();
        }
    }
      	
    /**
     * 미니홈피 정보 업데이트
     */
    @PostMapping("/updateMiniHome")
    public ResponseEntity<UpdateMiniHomeResponseDto> updateMiniHome(
    		@ModelAttribute MiniHomeUpdateRequestDto requestDto) {
        
        try {
        	// @ModelAttribute를 사용하여 요청 파라미터를 DTO에 자동 바인딩하므로,
            // 별도의 DTO 변환 로직이 필요 없음.
            UpdateMiniHomeResponseDto response = miniHomeService.updateMiniHome(requestDto);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            UpdateMiniHomeResponseDto errorResponse = UpdateMiniHomeResponseDto.builder()
                    .success(false)
                    .message("미니홈피 업데이트에 실패했습니다: " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

	
}
