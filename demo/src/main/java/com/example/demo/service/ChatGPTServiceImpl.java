package com.example.demo.service;

import com.example.demo.config.ChatGPTConfig;
import com.example.demo.dto.CompletionRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


import java.util.*;

@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    private final ChatGPTConfig chatGPTConfig;

    public ChatGPTServiceImpl(ChatGPTConfig chatGPTConfig) {
        this.chatGPTConfig = chatGPTConfig;
    }

    @Value("${openai.model}")
    private String model;

    @Override
    public List<Map<String, Object>> modelList() {
        log.debug("[+] 모델 리스트를 조회합니다.");
        List<Map<String, Object>> resultList = null;

        HttpHeaders headers = chatGPTConfig.httpHeaders();

        ResponseEntity<String> response = chatGPTConfig.restTemplate().exchange(
                "https://api.openai.com/v1/models",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);

        try {
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> data = om.readValue(response.getBody(), new TypeReference<>() {});

            resultList = (List<Map<String, Object>>) data.get("data");
            for (Map<String, Object> object : resultList) {
                log.debug("ID: " + object.get("id"));
                log.debug("Object: " + object.get("object"));
                log.debug("Created: " + object.get("created"));
                log.debug("Owned By: " + object.get("owned_by"));
            }
        } catch (JsonMappingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public Map<String, Object> isValidModel(String modelName) {
        log.debug("[+] 모델이 유효한지 조회합니다. 모델 : " + modelName);
        Map<String, Object> result;

        HttpHeaders headers = chatGPTConfig.httpHeaders();

        ResponseEntity<String> response = chatGPTConfig.restTemplate().exchange(
                "https://api.openai.com/v1/models/" + modelName,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);
        try {
            ObjectMapper om = new ObjectMapper();
            result = om.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    @Override
    public Map<String, Object> prompt(CompletionRequestDto completionRequestDto) {
        log.debug("[+] 프롬프트를 수행합니다.");

        Map<String, Object> result;
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        ObjectMapper om = new ObjectMapper();

        // 기존 코드에서 메시지를 객체로 감싸어 messages 속성에 추가
        List<CompletionRequestDto.Message> messageList = new ArrayList<>();
        messageList.add(new CompletionRequestDto.Message("user", "")); // 사용자가 입력한 질문 또는 대화의 시작 문장을 메시지 리스트에 추가
        completionRequestDto.setMessages(messageList);

        // 기존 코드와 동일하게 진행
        completionRequestDto.setModel(model);
        String requestBody;
        try {
            requestBody = om.writeValueAsString(completionRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = chatGPTConfig.restTemplate().exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                requestEntity,
                String.class);
        try {
            result = om.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
