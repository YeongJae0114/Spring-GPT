package chatGPT.gptserviceV3.db.service;

import chatGPT.gptserviceV3.db.domain.ReceiptInfo;
import chatGPT.gptserviceV3.db.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService{
    private final ReceiptRepository receiptRepository;

    @Override
    public ReceiptInfo saveReceiptInfo(ReceiptInfo receiptInfo) {
        return receiptRepository.save(receiptInfo);
    }

    @Override
    public List<ReceiptInfo> getAllReceiptInfo() {
        return receiptRepository.findAll();
    }

    @Override
    public Optional<ReceiptInfo> getReceiptInfoById(Long id) {
        return receiptRepository.findById(id);
    }

    @Override
    public String getImageById(Long id) {
        return getReceiptInfoById(id).map(ReceiptInfo::getBase64Image).orElse(null);
    }
}
