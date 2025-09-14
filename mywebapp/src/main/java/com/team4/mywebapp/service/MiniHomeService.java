package com.team4.mywebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.MiniHomeDto;
import com.team4.mywebapp.mapper.MiniHomeMapper;

@Service
public class MiniHomeService {
	
	@Autowired
	private MiniHomeMapper minihomeMapper;
	
	// 미니홈 정보 조회
	public MiniHomeDto getMiniHomeById(int minihomeId) {
		return minihomeMapper.getMiniHomeById(minihomeId);	
	}
	
	// 미니홈 수정 (전체)
	public int updateMiniHome(MiniHomeDto minihomeDto) {
		return minihomeMapper.updateMiniHome(minihomeDto);
	}
	

}
