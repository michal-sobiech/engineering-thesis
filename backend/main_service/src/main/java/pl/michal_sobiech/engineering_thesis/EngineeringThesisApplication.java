package pl.michal_sobiech.engineering_thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan
@ComponentScan(basePackages = "pl.michal_sobiech.infra")
@EnableJpaRepositories(basePackages = "pl.michal_sobiech.infra")
public class EngineeringThesisApplication {

    public static void main(String[] args) {
        System.out.println("MAIN SERVICE");
        SpringApplication.run(EngineeringThesisApplication.class, args);
    }

}
