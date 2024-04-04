package chatGPT.gptserviceV3.db.service;

import chatGPT.gptserviceV3.db.domain.ReceiptInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReceiptService {
    ReceiptInfo saveReceiptInfo(ReceiptInfo receiptInfo);

    List<ReceiptInfo> getAllReceiptInfo();

    Optional<ReceiptInfo>getReceiptInfoById(Long id);

    String getImageById(Long id);
}
