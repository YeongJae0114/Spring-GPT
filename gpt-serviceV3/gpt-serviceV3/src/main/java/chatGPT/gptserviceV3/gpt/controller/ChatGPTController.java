package chatGPT.gptserviceV3.gpt.controller;

import chatGPT.gptserviceV3.gpt.service.ChatGPTService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/chatGpt")
@RequiredArgsConstructor
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @GetMapping("/test")
    public ResponseEntity<String> analyzeImage() {
        try {
            // ImageService를 통해 이미지 분석 요청
            String analysisResult = chatGPTService.analyzeImage(2L);
            return ResponseEntity.ok(analysisResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("An error occurred while processing the image analysis.");
        }
    }

}
