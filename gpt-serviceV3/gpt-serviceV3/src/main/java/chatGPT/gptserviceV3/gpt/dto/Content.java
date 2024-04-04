package chatGPT.gptserviceV3.gpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Content {
    private String type;
    private String text;
    private ImageUrl imageUrl;

    @Builder
    public Content(String type, String text, ImageUrl imageUrl){
        this.type=type;
        this.text=text;
        this.imageUrl=imageUrl;
    }
}
