package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import pl.michal_sobiech.core.location.Location;

public record CreateEnterpriseCommand(

        String name,
        String description,
        Location location,
        Optional<MultipartFile> logoFile,
        Optional<MultipartFile> backgroundPhotoFile

) {

}
