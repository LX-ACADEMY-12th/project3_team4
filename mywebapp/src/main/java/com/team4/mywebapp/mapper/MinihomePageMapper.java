package com.team4.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.team4.mywebapp.dto.MinihomePageDTO;

@Mapper
public interface MinihomePageMapper {
    MinihomePageDTO getMinihomePage(@Param("minihomeOwnerId") int minihomeOwnerId);
<<<<<<< HEAD
}
=======
}
>>>>>>> 79eb279cc789d85736a242ff9e7895324b0b112f
