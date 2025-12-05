package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise.Enterprise;
import pl.michal_sobiech.core.enterprise.EnterpriseService;
import pl.michal_sobiech.core.entrepreneur.Entrepreneur;
import pl.michal_sobiech.core.model.File;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.file.FileMapper;

@Component
@RequiredArgsConstructor
public class EnterpriseControllerCreateEnterprise {

    private final AuthService authService;
    private final EnterpriseService enterpriseService;

    public ResponseEntity<CreateEnterpriseResponse> createEnterprise(CreateEnterpriseCommand command) {
        Entrepreneur entrepreneur = authService.requireEntrepreneur();

        Optional<File> logo = command.logoFile().map(FileMapper::fromMultipartFile);
        Optional<File> backgrounPhoto = command.backgroundPhotoFile().map(FileMapper::fromMultipartFile);

        Enterprise enterprise = enterpriseService.createEnterprise(entrepreneur.getUserId(), command.name(),
                command.description(), command.location().address(),
                command.location().longitude(), command.location().latitude(), logo, backgrounPhoto);
        var responseBody = new CreateEnterpriseResponse(enterprise.enterpriseId());
        return ResponseEntity.ok(responseBody);
    }

}
