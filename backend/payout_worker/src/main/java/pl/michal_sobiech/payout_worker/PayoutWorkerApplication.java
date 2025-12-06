package pl.michal_sobiech.payout_worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCaching
@ConfigurationPropertiesScan
@ComponentScan(basePackages = "pl.michal_sobiech")
@EnableJpaRepositories(basePackages = "pl.michal_sobiech.infra")
@EntityScan(basePackages = "pl.michal_sobiech.core")
@EnableScheduling
public class PayoutWorkerApplication {

    public static void main(String[] args) {
        System.out.println("PAYOUT WORKER");
        SpringApplication.run(PayoutWorkerApplication.class, args);
    }

}
