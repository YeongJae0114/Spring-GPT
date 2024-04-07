package chatGPT.gptserviceV3.gpt.dto;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageUrl {
    @Lob
    private String url;
}
