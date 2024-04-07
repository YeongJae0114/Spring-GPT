package chatGPT.gptserviceV3.gpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class Message {
    private String role;
    private List<Content> contents;

    @Builder
    public Message(String role, List<Content> contents){
        this.role=role;
        this.contents=contents;
    }
}
