package pl.michal_sobiech.engineering_thesis.user;

import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UsernameNamespaceConverter implements AttributeConverter<UsernameNamespace, String> {

    private final BiMap<UsernameNamespace, String> map = HashBiMap.create(Map.of(
            UsernameNamespace.ADMIN, "admin",
            UsernameNamespace.EMAIL, "email",
            UsernameNamespace.EMPLOYEE, "employee"));

    @Override
    public String convertToDatabaseColumn(UsernameNamespace attribute) {
        return attribute == null
                ? null
                : map.get(attribute);
    }

    @Override
    public UsernameNamespace convertToEntityAttribute(String databaseValue) {
        return databaseValue == null
                ? null
                : map.inverse().get(databaseValue);
    }

}
