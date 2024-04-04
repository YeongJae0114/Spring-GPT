package chatGPT.gptserviceV3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("chatGPT.gptserviceV3.db.domain")
@EnableJpaRepositories("chatGPT.gptserviceV3.db.repository")
@SpringBootApplication
public class GptServiceV3Application {

	public static void main(String[] args) {
		SpringApplication.run(GptServiceV3Application.class, args);
	}

}
