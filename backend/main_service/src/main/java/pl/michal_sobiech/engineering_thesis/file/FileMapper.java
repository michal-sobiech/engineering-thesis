package pl.michal_sobiech.engineering_thesis.file;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import pl.michal_sobiech.core.model.File;

public class FileMapper {

    public static File fromMultipartFile(MultipartFile file) {
        try {
            return new File(
                    file.getOriginalFilename(),
                    file.getBytes());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
