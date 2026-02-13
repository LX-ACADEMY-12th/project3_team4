package com.team4.mywebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.MiniHomeDto;
import com.team4.mywebapp.mapper.MiniHomeMapper;

@Service // 이 클래스가 서비스 계층의 컴포넌트임을 나타냄.
public class MiniHomeService {

	@Autowired // Spirng이 MiniHomeMapper의 인스턴스를 자동으로 주입
	private MiniHomeMapper miniHomeMapper;

	// 미니홈피 정보를 조회하는 비즈니스 메서드
	public MiniHomeDto getMiniHomeByUserId(String userId) {
		try {
			// 매퍼를 호출하여 DB에서 데이터를 가져옵니다.
			MiniHomeDto miniHomeInfo = miniHomeMapper.getMiniHomeByUserId(userId);

			// 조회된 데이터가 null 인지 확인하고, 필요한 경우 예외를 처리하거니 기본값을 반환
			if (miniHomeInfo == null) {
				// 사용자에게 미니홈피 정보가 없음을 알리는 예외를 던집니다.
				throw new IllegalArgumentException("해당 사용자의 미니홈피 정보를 찾을 수 없습니다.");
			}

			return miniHomeInfo;
		} catch (Exception e) {
			// DB 연결 실패 또는 기타 예외 발생 시 로그 기록
			System.err.println("DB 연결 또는 쿼리 실행에 실패했습니다. userId: " + userId);
			e.printStackTrace(); // 상세한 스택 트레이스 출력
			throw new RuntimeException("데이터베이스 연결 오류", e); // 더 높은 계층으로 예외 재전달
		}
	}

//	// 미니홈 수정 (전체)
//	public int updateMiniHome(MiniHomeDto minihomeDto) {
//		return minihomeMapper.updateMiniHome(minihomeDto);
//	}

}
