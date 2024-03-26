package com.example.demo.controller;

import com.example.demo.dto.CompletionRequestDto;
import com.example.demo.service.ChatGPTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/chatGpt")
public class ChatGPTController {


    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }
    @ResponseBody
    @PostMapping("/test")
    public String test(@RequestParam String base64
    ){
        log.info("{}",base64);

        if (base64.isEmpty()){
            log.info("실패");
            return "실패";
        }else{
            log.info("성공");
            return "성공";
        }
    }



    /**
     * [API] ChatGPT 모델 리스트를 조회합니다.
     */
    @GetMapping("/modelList")
    public ResponseEntity<List<Map<String, Object>>> selectModelList() {
        List<Map<String, Object>> result = chatGPTService.modelList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] ChatGPT 유효한 모델인지 조회합니다.
     * @param modelName
     * @return
     */
    @GetMapping("/model")
    public ResponseEntity<Map<String, Object>> isValidModel(@RequestParam(name = "modelName") String modelName) {
        Map<String, Object> result = chatGPTService.isValidModel(modelName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] ChatGPT 모델 리스트를 조회합니다.
     */
    @PostMapping("/prompt")
    public ResponseEntity<Map<String, Object>> selectPrompt(@RequestBody CompletionRequestDto completionRequestDto) {
        Map<String, Object> result = chatGPTService.prompt(completionRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
