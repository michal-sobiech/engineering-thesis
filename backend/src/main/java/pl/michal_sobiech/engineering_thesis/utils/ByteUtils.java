package pl.michal_sobiech.engineering_thesis.utils;

import java.sql.Blob;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class ByteUtils {
    public static Resource createResource(Blob blob) {
        try {
            int numBytes = (int) blob.length();
            byte[] bytes = blob.getBytes(1, numBytes);
            return new ByteArrayResource(bytes);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create Resource");
        }
    }
}
