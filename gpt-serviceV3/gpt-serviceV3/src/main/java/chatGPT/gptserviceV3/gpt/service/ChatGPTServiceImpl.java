package chatGPT.gptserviceV3.gpt.service;

import chatGPT.gptserviceV3.db.repository.ReceiptRepository;
import chatGPT.gptserviceV3.db.service.ReceiptService;
import chatGPT.gptserviceV3.gpt.config.ChatGPTConfig;
import chatGPT.gptserviceV3.gpt.dto.Content;
import chatGPT.gptserviceV3.gpt.dto.ImageAnalysisRequestDto;
import chatGPT.gptserviceV3.gpt.dto.ImageUrl;
import chatGPT.gptserviceV3.gpt.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatGPTServiceImpl implements ChatGPTService{
    private final ChatGPTConfig chatGPTConfig;
    private final ReceiptService receiptService;

    @Value("${openai.url.prompt}")
    private String promptUrl;


    @Override
    public String analyzeImage(Long id) {
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        ImageUrl imageUrl = new ImageUrl();
        imageUrl.setUrl("data:image/jpeg;base64," + receiptService.getImageById(id));

        //log.info(receiptService.getImageById(id));
        Content imageContent = Content.builder()
                .type("image_url")
                .imageUrl(imageUrl)
                .build();

        Content textContent = Content.builder()
                .type("text")
                .text("What’s in this image?")
                .build();

        Message message = Message.builder()
                .role("user")
                .contents(Arrays.asList(textContent, imageContent))
                .build();

        ImageAnalysisRequestDto requestDto = ImageAnalysisRequestDto.builder()
                .model("gpt-4-vision-preview")
                .messages(Collections.singletonList(message))
                .build();

        HttpEntity<ImageAnalysisRequestDto> entity = new HttpEntity<>(requestDto, headers);
        return chatGPTConfig.restTemplate().postForObject(promptUrl, entity, String.class);
    }
}
