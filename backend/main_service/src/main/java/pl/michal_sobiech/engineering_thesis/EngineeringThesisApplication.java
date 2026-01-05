package pl.michal_sobiech.engineering_thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCaching
@ConfigurationPropertiesScan
@ComponentScan(basePackages = "pl.michal_sobiech")
@EnableJpaRepositories(basePackages = "pl.michal_sobiech.infra")
@EntityScan(basePackages = "pl.michal_sobiech.core")
public class EngineeringThesisApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineeringThesisApplication.class, args);
    }

}
