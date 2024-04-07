package gptdto.gptserviceV4.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gptdto.gptserviceV4.config.ChatGPTConfig;
import gptdto.gptserviceV4.dto.ContentDto;
import gptdto.gptserviceV4.dto.ImageAnalysisRequestDto;
import gptdto.gptserviceV4.dto.ImageURLDto;
import gptdto.gptserviceV4.dto.MessageDto;
import gptdto.gptserviceV4.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGPTServiceImpl implements ChatGPTService {
    @Value("${openai.url.prompt}")
    private String promptUrl;

    private final ChatGPTConfig chatGPTConfig;

    @Override
    public Map<String, Object> promptV2(ImageURLDto imageUrlDto) {

        Map<String, Object> resultMap = new HashMap<>();
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        ContentDto imageContent = new ContentDto("image_url", imageUrlDto);
        ContentDto textContent = new ContentDto("text", "What’s in this image?");
        MessageDto message = new MessageDto("user", Arrays.asList(textContent, imageContent));


        ImageAnalysisRequestDto requestDto = new ImageAnalysisRequestDto("gpt-4-vision-preview", Collections.singletonList(message));
        // 요청 엔티티 생성
        HttpEntity<ImageAnalysisRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = chatGPTConfig.restTemplate()
                .exchange(promptUrl, HttpMethod.POST, requestEntity, String.class);

        try {
            // GPT API 로부터 받은 JSON 형식의 응답 문자 파싱
            ObjectMapper om = new ObjectMapper();
            resultMap = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> prompt(ImageAnalysisRequestDto imageAnalysisRequestDt) {

        Map<String, Object> resultMap = new HashMap<>();
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // 요청 엔티티 생성
        HttpEntity<ImageAnalysisRequestDto> requestEntity = new HttpEntity<>(imageAnalysisRequestDt, headers);

        // OpenAI GPT API 요청 -> 응답을 저장
        ResponseEntity<String> response = chatGPTConfig.restTemplate()
                .exchange(promptUrl, HttpMethod.POST, requestEntity, String.class);

        try {
            // GPT API 로부터 받은 JSON 형식의 응답 문자 파싱
            ObjectMapper om = new ObjectMapper();
            resultMap = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultMap;
    }
}
