package pl.michal_sobiech.engineering_thesis.jackson;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;

@Configuration
public class JacksonConfig {

    @Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }
}
