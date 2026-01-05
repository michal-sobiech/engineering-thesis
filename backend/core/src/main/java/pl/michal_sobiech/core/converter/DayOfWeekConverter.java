package pl.michal_sobiech.core.converter;

import java.time.DayOfWeek;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.michal_sobiech.core.utils.DayOfWeekUtils;

@Converter
public class DayOfWeekConverter implements AttributeConverter<DayOfWeek, Short> {

    @Override
    public Short convertToDatabaseColumn(DayOfWeek attribute) {
        return attribute == null
                ? null
                : DayOfWeekUtils.getNumberOfDayOfWeek(attribute);
    }

    @Override
    public DayOfWeek convertToEntityAttribute(Short databaseValue) {
        return databaseValue == null
                ? null
                : DayOfWeekUtils.getDayOfWeek(databaseValue);
    }

}
