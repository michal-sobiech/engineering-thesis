package pl.michal_sobiech.engineering_thesis.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.ZoneIdConverter;

import pl.michal_sobiech.core.converter.DayOfWeekConverter;

@Configuration
public class ConverterConfig {

    @Bean
    public ZoneIdConverter zoneIdConverter() {
        return new ZoneIdConverter();
    }

    @Bean
    public DayOfWeekConverter dayOfWeekConverter() {
        return new DayOfWeekConverter();
    }

}
