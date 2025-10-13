package pl.michal_sobiech.engineering_thesis.enterprise;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public record PatchEnterpriseRequestDto(

        long enterpriseId,
        Optional<String> name,
        Optional<String> description,
        Optional<String> location,
        Optional<MultipartFile> logoFile,
        Optional<MultipartFile> backgroundPhotoFile

) {

}
