package com.example.demo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatGPTConfig {
    @Value("${openai.secret-key}")
    private String secretKey;

    // Spring에서 지원하는 객체로 간편하게 Rest 방식 API를 호출할 수 있는 Spring 내장 클래스
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    // WT 토큰으로 Bearer 토큰 값을 입력하여서 전송하기 위한 공통 Header를 구성
    @Bean
    public HttpHeaders httpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        // "Bearer " 문자열 맨 뒤에 공백을 입력해야함.
        headers.set("Authorization", "Bearer " + secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
