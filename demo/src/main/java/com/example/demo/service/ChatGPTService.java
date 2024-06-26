package com.example.demo.service;

import com.example.demo.dto.CompletionRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public interface ChatGPTService {
    List<Map<String, Object>> modelList();

    Map<String, Object> prompt(CompletionRequestDto completionRequestDto);

    Map<String, Object> isValidModel(String modelName);
}
