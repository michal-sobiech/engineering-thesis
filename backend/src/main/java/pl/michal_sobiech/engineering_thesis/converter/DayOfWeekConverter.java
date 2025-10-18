package pl.michal_sobiech.engineering_thesis.converter;

import java.time.DayOfWeek;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.michal_sobiech.engineering_thesis.utils.DayOfWeekUtils;

@Converter(autoApply = true)
public class DayOfWeekConverter implements AttributeConverter<DayOfWeek, Short> {

    @Override
    public Short convertToDatabaseColumn(DayOfWeek attribute) {
        return DayOfWeekUtils.getNumberOfDayOfWeek(attribute);
    }

    @Override
    public DayOfWeek convertToEntityAttribute(Short databaseValue) {
        return DayOfWeekUtils.getDayOfWeek(databaseValue);
    }

}
