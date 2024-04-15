package GPTSpring.gptserviceV5.service.impl;

import GPTSpring.gptserviceV5.config.ChatGPTConfig;
import GPTSpring.gptserviceV5.dto.ContentDto;
import GPTSpring.gptserviceV5.dto.ImageAnalysisRequestDto;
import GPTSpring.gptserviceV5.dto.MessageDto;
import GPTSpring.gptserviceV5.dto.Response_format;
import GPTSpring.gptserviceV5.service.ChatGPTService;
import GPTSpring.gptserviceV5.service.ResponseToService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
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
public class ResponseToServiceImpl implements ResponseToService {
    private final ChatGPTConfig chatGPTConfig;

    @Value("${openai.url.prompt}")
    private String promptUrl;


    String question1= "This is the text I extracted from the receipt, and I want to change the item and total price to json format.";
    @Override
    public String responseData(Map<String, Object> response) {
        String contentValue = (String) response.get("content");
        // 결과를 담을 Map
        Map<String, Object> resultMap = new HashMap<>();
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        ContentDto textContent = new ContentDto("text", question1 + contentValue);
        MessageDto message = new MessageDto("user", Arrays.asList(textContent));

        // 응답 데이터 포멧 설정
        Response_format response_format = new Response_format("json_object");

        ImageAnalysisRequestDto requestDto = new ImageAnalysisRequestDto("gpt-3.5-turbo-1106", Collections.singletonList(message), response_format);
        // 요청 엔티티 생성
        return getStringObjectMap(resultMap, headers, requestDto);
    }

    @Override
    public Map<String, Object> stringToJson(String newResult) {
        JSONParser jsonParser = new JSONParser(newResult);
        System.out.println(jsonParser);

        return null;
    }

    private String getStringObjectMap(Map<String, Object> resultMap, HttpHeaders headers, ImageAnalysisRequestDto requestDto) {
        HttpEntity<ImageAnalysisRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = chatGPTConfig.restTemplate()
                .exchange(promptUrl, HttpMethod.POST, requestEntity, String.class);
        String contentValue = null;

        try {
            // GPT API 로부터 받은 JSON 형식의 응답 문자 파싱
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> responseMap = om.readValue(response.getBody(), new TypeReference<>() {});

            // responseMap에서 'choices' 리스트를 추출하고 그 안의 'content' 값을 얻습니다.
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                contentValue = (String) message.get("content");
            }
        } catch (JsonProcessingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }

        return contentValue;
    }
}
