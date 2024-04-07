package gptdto.gptserviceV4.service;

import gptdto.gptserviceV4.dto.ImageAnalysisRequestDto;
import gptdto.gptserviceV4.dto.ImageURLDto;

import java.util.Map;

public interface ChatGPTService {
    public Map<String, Object> prompt(ImageAnalysisRequestDto imageAnalysisRequestDt);

    public Map<String, Object> promptV2(ImageURLDto imageURLDto);
}
