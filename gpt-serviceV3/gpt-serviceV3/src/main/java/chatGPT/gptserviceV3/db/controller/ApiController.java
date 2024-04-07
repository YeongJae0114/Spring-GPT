package chatGPT.gptserviceV3.db.controller;

import chatGPT.gptserviceV3.db.domain.ReceiptInfo;
import chatGPT.gptserviceV3.db.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receiptInfo")
public class ApiController {
    private final ReceiptService receiptService;

    // 모든 저장된 정보 조회
    @GetMapping
    public List<ReceiptInfo> receiptInfoList(){
        return receiptService.getAllReceiptInfo();
    }

    // id 로 저장된 이미지 조회
    @GetMapping("/{id}")
    public ResponseEntity<ReceiptInfo> getParkingInfoById(@PathVariable Long id) {
        Optional<ReceiptInfo> parkingInfoOptional = receiptService.getReceiptInfoById(id);

        return parkingInfoOptional
                .map(parkingInfo -> ResponseEntity.ok().body(parkingInfo))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 이미지 저장
    @PatchMapping("/save")
    public ResponseEntity<String> save(@RequestParam("name") String name,
                                       @RequestParam("base64Image") String base64Image){
        try {
            ReceiptInfo receiptInfo = new ReceiptInfo();
            receiptInfo.setName(name);
            receiptInfo.setBase64Image(base64Image);

            receiptService.saveReceiptInfo(receiptInfo);
            return ResponseEntity.ok("Data saved successfully");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save the data and image.");
        }
    }
}
