package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletionRequestDto {
    private String model;
    private List<Message> messages; // 메시지 객체의 리스트
    private float temperature;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role; // 역할 (e.g., "system", "user")
        private String content; // 메시지 내용
    }
}
