package pl.michal_sobiech.payout_worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan
public class PayoutWorkerApplication {

    public static void main(String[] args) {
        System.out.println("PAYOUT WORKER");
        SpringApplication.run(PayoutWorkerApplication.class, args);
    }

}
