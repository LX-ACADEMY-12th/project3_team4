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
        MinihomePageDTO dto = minihomePageMapper.getMinihomePage(minihomeOwnerId);
        if (dto != null) {
            int today = dto.getTodayCount() == null ? 0 : dto.getTodayCount();
            int total = dto.getTotalCount() == null ? 0 : dto.getTotalCount();
            dto.setVisitCount(new VisitCount(today, total));
        }
        return dto;
    }
}