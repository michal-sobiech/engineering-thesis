package pl.michal_sobiech.engineering_thesis.utils;

import java.util.Optional;

import org.openapitools.jackson.nullable.JsonNullable;

public class JsonNullableUtils {

    public static <T> Optional<T> jsonNullableToOptional(JsonNullable<T> jsonNullable) {
        if (jsonNullable.isPresent()) {
            return Optional.of(jsonNullable.get());
        } else {
            return Optional.empty();
        }
    }

}
