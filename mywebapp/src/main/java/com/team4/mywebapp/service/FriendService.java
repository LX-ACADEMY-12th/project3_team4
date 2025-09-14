package com.team4.mywebapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.mywebapp.dto.UserDto;
import com.team4.mywebapp.mapper.FriendMapper;

/**
 * 친구 관련 비즈니스 로직을 처리하는 서비스 클래스
 * 사용자 조회, 친구 관계 관리 등의 기능을 제공
 * 
 * @author Team4
 * @version 1.0
 */
@Service // ← 중요: 이 어노테이션이 없으면 Spring Bean으로 등록되지 않음!
public class FriendService {
    
    /**
     * 데이터베이스 접근을 위한 MyBatis 매퍼
     */
    @Autowired
    private FriendMapper friendMapper;

    /**
     * 전체 사용자 목록을 조회하는 비즈니스 메소드
     * 친구 추가를 위한 사용자 검색에 활용
     * 
     * @return List<UserDto> 전체 사용자 목록
     * @throws RuntimeException 데이터베이스 조회 실패 시 발생
     */
    public List<UserDto> getAllUsers() {
        try {
            // 매퍼를 통해 데이터베이스에서 전체 사용자 조회
            List<UserDto> users = friendMapper.getAllUsers();
            
            // null 체크 (매퍼에서 null을 반환할 가능성 대비)
            if (users == null) {
                throw new RuntimeException("사용자 데이터를 불러올 수 없습니다.");
            }
            
            return users;
            
        } catch (Exception e) {
            // 데이터베이스 조회 중 오류 발생 시 로그 출력 후 예외 던지기
            System.err.println("getAllUsers() 실행 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("사용자 목록 조회 중 오류가 발생했습니다.", e);
        }
    }
}
