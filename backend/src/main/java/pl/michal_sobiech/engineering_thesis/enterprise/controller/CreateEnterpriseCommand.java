package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;
import org.springframework.web.multipart.MultipartFile;

public record CreateEnterpriseCommand(

        String name,
        String description,
        Location location,
        Optional<MultipartFile> logoFile,
        Optional<MultipartFile> backgroundPhotoFile

) {

}
