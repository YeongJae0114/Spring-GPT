package gptdto.gptserviceV4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageURLDto {
    @Lob @JsonProperty("url")
    private String url;
    // 생성자, getters 및 setters

    public ImageURLDto() {}

    public ImageURLDto(String url) {
        this.url = url;
    }
}
