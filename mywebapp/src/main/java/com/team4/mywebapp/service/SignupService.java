package com.team4.mywebapp.service;

import com.team4.mywebapp.dto.MiniHomeDto;
import com.team4.mywebapp.dto.MiniHomeInsertDto;
import com.team4.mywebapp.dto.SignupDto;
import com.team4.mywebapp.mapper.MiniHomeMapper;
import com.team4.mywebapp.mapper.SignupMapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 이 클래스가 서비스 컴포넌트임을 나타냅니다.
public class SignupService {

    @Autowired
    private SignupMapper signupMapper;
    
    @Autowired
    private MiniHomeMapper minihomeMapper;

    /**
     * 아이디 중복 여부를 확인하는 비즈니스 메서드입니다.
     * @param loginId 확인할 아이디
     * @return 중복이 아니면 true, 중복이면 false
     */
    public boolean isIdAvailable(String loginId) {
        int count = signupMapper.checkDuplicateId(loginId);
        // count가 0이면 중복이 아니므로 true 반환
        return count == 0;
    }

    /**
     * 회원가입 처리를 수행하는 비즈니스 메서드입니다.
     * @param signupDto 회원 정보를 담은 객체
     * @return 회원가입 성공 시 true, 실패 시 false
     */
    @Transactional
    public boolean signup(SignupDto signupDto) {
        // 이미 프론트엔드에서 중복 확인을 했지만,
        // 서버 측에서 한 번 더 확인하여 이중 검증을 하는 것이 좋습니다.
        if (!isIdAvailable(signupDto.getLoginId())) {
            return false; // 아이디가 이미 존재하면 실패
        }
        
        // 1. 사용자 정보를 데이터 베이스에 삽입
        // 회원 정보를 데이터베이스에 삽입하고, 삽입된 행의 수를 반환받습니다.
        int insertedRows = signupMapper.insertUser(signupDto);
        
        // 2. userId 값을 확인하는 로그 추가
        System.out.println("사용자 삽입 성공, 생성된 userId: " + signupDto.getUserId());

        // 3. 미니홈피 생성 로직
        MiniHomeInsertDto minihomeInsertDto = new MiniHomeInsertDto();
        minihomeInsertDto.setUserId(signupDto.getUserId()); // 이제 올바른 userId 사용 가능
        minihomeInsertDto.setCreatedAt(LocalDateTime.now());
        minihomeMapper.insertMiniHome(minihomeInsertDto);

        // 미니홈피 정보 삽입 성공 로그
        System.out.println("미니홈피 생성 성공: ownerId " + minihomeInsertDto.getUserId());
        
        // 1개 행이 성공적으로 삽입되면 true 반환
        return insertedRows == 1;
    }
}