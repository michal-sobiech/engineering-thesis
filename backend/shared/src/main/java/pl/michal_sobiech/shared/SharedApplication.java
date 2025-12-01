package pl.michal_sobiech.shared;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan
public class SharedApplication {

    public static void main(String[] args) {
        System.out.println("SHARED");
        SpringApplication.run(SharedApplication.class, args);
    }

}
