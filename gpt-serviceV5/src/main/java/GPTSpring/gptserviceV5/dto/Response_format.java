package GPTSpring.gptserviceV5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


public class Response_format {
    @JsonProperty("type")
    private String type;

    public Response_format(String type) {
        this.type = type;
    }
}
