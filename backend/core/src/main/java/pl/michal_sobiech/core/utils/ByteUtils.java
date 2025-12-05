package pl.michal_sobiech.core.utils;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class ByteUtils {
    public static Resource createResource(byte[] bytes) {
        try {
            return new ByteArrayResource(bytes);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create Resource");
        }
    }
}
