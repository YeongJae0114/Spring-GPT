package gptdto.gptserviceV4.controller;

import gptdto.gptserviceV4.dto.ImageAnalysisRequestDto;
import gptdto.gptserviceV4.dto.ImageURLDto;
import gptdto.gptserviceV4.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/chatGpt")
@RequiredArgsConstructor
public class ChatGPTController {
    private final ChatGPTService chatGPTService;

    @PostMapping("/prompt")
    public ResponseEntity<Map<String, Object>> selectPrompt(@RequestBody ImageAnalysisRequestDto imageAnalysisRequestDto){
        Map<String, Object> result = chatGPTService.prompt(imageAnalysisRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/imagePrompt")
    public ResponseEntity<Map<String, Object>> imagePrompt(@RequestBody ImageURLDto imageURLDto){
        Map<String, Object> result = chatGPTService.promptV2(imageURLDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
