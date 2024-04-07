package chatGPT.gptserviceV3.gpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
@Getter @Setter
public class ImageAnalysisRequestDto {
    private String model;
    private List<Message>messages;

    @Builder
    public ImageAnalysisRequestDto(String model, List<Message> messages){
        this.model = model;
        this.messages = messages;
    }
}
