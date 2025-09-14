package com.team4.mywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.team4.mywebapp.dto.MinihomePageDTO;
import com.team4.mywebapp.service.MinihomePageService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class MinihomePageController {

    @Autowired
    private MinihomePageService minihomePageService;

    @GetMapping("/showMiniHome")
    public ResponseEntity<MinihomePageDTO> getMinihomePage(@RequestParam int userId) {
        MinihomePageDTO dto = minihomePageService.getMinihomePage(userId);
        return ResponseEntity.ok(dto);
    }
}