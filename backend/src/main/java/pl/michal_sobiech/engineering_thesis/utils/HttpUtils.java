package pl.michal_sobiech.engineering_thesis.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpUtils {

    public static <T> ResponseEntity<T> createUnauthorizedResponse() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public static <T> ResponseEntity<T> createForbiddenResponse() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
