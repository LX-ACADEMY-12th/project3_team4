package com.team4.mywebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링 컨테이너에 빈을 등록하는 설정 클래스임을 선언
@EnableWebSecurity // 스프링 시큐리티를 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // REST API이므로 CSRF 보호를 비활성화합니다.
            .authorizeHttpRequests(authorize -> authorize
                // "/api" 경로로 시작하는 모든 요청은 인증 없이 허용합니다.
                .requestMatchers("/api/**").permitAll() 
                // 그 외의 모든 요청은 인증이 필요합니다.
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
