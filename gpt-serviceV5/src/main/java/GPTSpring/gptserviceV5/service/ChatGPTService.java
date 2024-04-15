package GPTSpring.gptserviceV5.service;


import GPTSpring.gptserviceV5.dto.ImageAnalysisRequestDto;
import GPTSpring.gptserviceV5.dto.ImageURLDto;

import java.util.Map;

public interface ChatGPTService {
     Map<String, Object> prompt(ImageAnalysisRequestDto imageAnalysisRequestDt);

     Map<String, Object> promptV2(ImageURLDto imageURLDto);
}
