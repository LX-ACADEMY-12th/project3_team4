package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.team4.mywebapp.dto.MinihomePageDTO;

@Mapper
public interface MinihomePageMapper {
    MinihomePageDTO getMinihomePage(@Param("minihomeOwnerId") int minihomeOwnerId);
}
