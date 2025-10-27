package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Component
@Converter(autoApply = true)
public class EnterpriseServiceCathegoryConverter implements AttributeConverter<EnterpriseServiceCathegory, String> {

    private final BiMap<EnterpriseServiceCathegory, String> map = HashBiMap.create(Map.ofEntries(
            Map.entry(EnterpriseServiceCathegory.BEAUTY_AND_WELLNESS, "BEAUTY_AND_WELLNESS"),
            Map.entry(EnterpriseServiceCathegory.HAIRDRESSER, "HAIRDRESSER"),
            Map.entry(EnterpriseServiceCathegory.BARBER, "BARBER"),
            Map.entry(EnterpriseServiceCathegory.FAMILY_MEDICINE, "FAMILY_MEDICINE"),
            Map.entry(EnterpriseServiceCathegory.INTERNIST, "INTERNIST"),
            Map.entry(EnterpriseServiceCathegory.PEDIATRICS, "PEDIATRICS"),
            Map.entry(EnterpriseServiceCathegory.PSYCHIATRY, "PSYCHIATRY"),
            Map.entry(EnterpriseServiceCathegory.STOMATOGLOGY, "STOMATOLOGY"),
            Map.entry(EnterpriseServiceCathegory.GYNECOLOGY, "GYNECOLOGY"),
            Map.entry(EnterpriseServiceCathegory.HANDYMAN, "HANDYMAN"),
            Map.entry(EnterpriseServiceCathegory.PLUMBER, "PLUMBER"),
            Map.entry(EnterpriseServiceCathegory.ELECTRICICAN, "ELECTRICIAN")));

    @Override
    public String convertToDatabaseColumn(EnterpriseServiceCathegory attribute) {
        return attribute == null
                ? null
                : map.get(attribute);
    }

    @Override
    public EnterpriseServiceCathegory convertToEntityAttribute(String databaseValue) {
        return databaseValue == null
                ? null
                : map.inverse().get(databaseValue);
    }

}
