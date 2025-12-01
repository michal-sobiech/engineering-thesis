package pl.michal_sobiech.engineering_thesis.converter;

import java.time.ZoneId;

import org.springframework.stereotype.Component;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Component
@Converter(autoApply = true)
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {

    @Override
    public String convertToDatabaseColumn(ZoneId attribute) {
        return attribute == null
                ? null
                : attribute.getId();
    }

    @Override
    public ZoneId convertToEntityAttribute(String databaseValue) {
        return databaseValue == null
                ? null
                : ZoneId.of(databaseValue);
    }

}
