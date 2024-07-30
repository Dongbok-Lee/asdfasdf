package ssafy.closetoyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClosetoyouApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClosetoyouApplication.class, args);
	}

}
