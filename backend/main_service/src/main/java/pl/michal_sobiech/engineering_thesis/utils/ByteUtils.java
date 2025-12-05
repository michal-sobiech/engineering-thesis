package pl.michal_sobiech.engineering_thesis.utils;

import org.springframework.core.io.ByteArrayResource;

public class ByteUtils {
    public static Resource createResource(byte[] bytes) {
        try {
            return new ByteArrayResource(bytes);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create Resource");
        }
    }
}
