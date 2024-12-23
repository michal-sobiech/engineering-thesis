package pl.michal_sobiech.engineering_thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EngineeringThesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngineeringThesisApplication.class, args);
	}

}
