package pl.michal_sobiech.core.enterprise;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;
import org.springframework.web.multipart.MultipartFile;

public record PatchEnterpriseRequestDto(

        long enterpriseId,
        Optional<String> name,
        Optional<String> description,
        Optional<Location> location,
        Optional<MultipartFile> logoFile,
        Optional<MultipartFile> backgroundPhotoFile

) {

}
