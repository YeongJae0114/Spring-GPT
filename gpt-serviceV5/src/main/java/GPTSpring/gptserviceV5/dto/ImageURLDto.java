package GPTSpring.gptserviceV5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageURLDto {
    @Lob @JsonProperty("url")
    private String url;
}
