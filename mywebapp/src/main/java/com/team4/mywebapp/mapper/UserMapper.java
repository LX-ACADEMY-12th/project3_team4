package com.team4.mywebapp.mapper;

import com.team4.mywebapp.dto.UserUpdateDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 사용자 기본 정보를 업데이트하는 메서드
     * @param userUpdateDto 업데이트할 사용자 정보가 담긴 DTO
     */
    void updateUserInfo(UserUpdateDto userUpdateDto);
}