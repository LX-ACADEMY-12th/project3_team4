//package com.team4.mywebapp.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // application.properties의 경로와 일치하도록 수정
//        String resourcePath = "file:src/main/resources/static/uploads/";
//        
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations(resourcePath);
//        
//        System.out.println("정적 파일 경로 설정: " + resourcePath);
//    }
//}