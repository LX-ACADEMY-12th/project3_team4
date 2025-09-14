package com.team4.mywebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.MinihomePageDTO;
import com.team4.mywebapp.dto.MinihomePageDTO.VisitCount;
import com.team4.mywebapp.mapper.MinihomePageMapper;

@Service
public class MinihomePageService {

    @Autowired
    private MinihomePageMapper minihomePageMapper;

    public MinihomePageDTO getMinihomePage(int minihomeOwnerId) {
        try {
            System.out.println("DB에서 사용자 정보 조회 - userId: " + minihomeOwnerId);
            
            MinihomePageDTO dto = minihomePageMapper.getMinihomePage(minihomeOwnerId);
            
            if (dto != null) {
                // 방문자 수 설정
                int today = dto.getTodayCount() == null ? 0 : dto.getTodayCount();
                int total = dto.getTotalCount() == null ? 0 : dto.getTotalCount();
                dto.setVisitCount(new VisitCount(today, total));
                
                // DB에 없는 필드들 기본값 설정
                if (dto.getTodayMood() == null) {
                    dto.setTodayMood(""); // 빈 값으로 설정
                }
                if (dto.getYoutubeVideoId() == null) {
                    dto.setYoutubeVideoId(""); // 빈 값으로 설정
                }
                
                System.out.println("DB 조회 성공 - " + dto.getNickname());
            } else {
                System.out.println("해당 사용자의 미니홈피를 찾을 수 없습니다.");
            }
            
            return dto;
            
        } catch (Exception e) {
            System.err.println("DB 조회 중 에러 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}