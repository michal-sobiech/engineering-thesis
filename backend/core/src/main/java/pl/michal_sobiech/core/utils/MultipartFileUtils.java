package pl.michal_sobiech.core.utils;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileUtils {

    public static byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
