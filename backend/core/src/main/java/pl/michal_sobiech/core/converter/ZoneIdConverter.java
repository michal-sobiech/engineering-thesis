package pl.michal_sobiech.core.converter;

import java.time.ZoneId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
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
