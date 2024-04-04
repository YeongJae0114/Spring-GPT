package chatGPT.gptserviceV3.db.repository;

import chatGPT.gptserviceV3.db.domain.ReceiptInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReceiptRepository extends JpaRepository<ReceiptInfo, Long> {

}
