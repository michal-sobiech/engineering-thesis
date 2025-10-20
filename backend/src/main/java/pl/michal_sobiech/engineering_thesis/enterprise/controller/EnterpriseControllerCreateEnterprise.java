package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.auth.EntrepreneurAuthContext;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;

@Component
@RequiredArgsConstructor
public class EnterpriseControllerCreateEnterprise {

    private final AuthService authService;
    private final EnterpriseService enterpriseService;

    public ResponseEntity<CreateEnterpriseResponse> createEnterprise(CreateEnterpriseCommand command) {
        EntrepreneurAuthContext entrepreneurAuthContext = authService.requireEntrepreneur();
        Entrepreneur entrepreneur = entrepreneurAuthContext.entrepreneur();

        final Enterprise enterprise = enterpriseService.createEnterprise(entrepreneur.getEntrepreneurId(), command);
        final var responseBody = new CreateEnterpriseResponse(enterprise.getEnterpriseId());
        return ResponseEntity.ok(responseBody);
    }

}
