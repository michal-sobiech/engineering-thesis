package pl.michal_sobiech.engineering_thesis.user;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Component
@Converter(autoApply = true)
public class UserGroupConverter implements AttributeConverter<UserGroup, String> {

    private final BiMap<UserGroup, String> map = HashBiMap.create(Map.of(
            UserGroup.CUSTOMER, "customer",
            UserGroup.EMPLOYEE, "employee",
            UserGroup.ENTREPRENEUR, "entrepreneur",
            UserGroup.HEAD_ADMIN, "head_admin",
            UserGroup.REGULAR_ADMIN, "regular_admin"));

    @Override
    public String convertToDatabaseColumn(UserGroup attribute) {
        return attribute == null
                ? null
                : map.get(attribute);
    }

    @Override
    public UserGroup convertToEntityAttribute(String databaseValue) {
        return databaseValue == null
                ? null
                : map.inverse().get(databaseValue);
    }

}
