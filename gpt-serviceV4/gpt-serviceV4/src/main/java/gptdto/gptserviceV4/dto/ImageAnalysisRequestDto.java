package gptdto.gptserviceV4.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class ImageAnalysisRequestDto {
    private String model;
    private List<MessageDto> messages;

    public ImageAnalysisRequestDto(String model, List<MessageDto> messages) {
        this.model = model;
        this.messages = messages;
    }
}
