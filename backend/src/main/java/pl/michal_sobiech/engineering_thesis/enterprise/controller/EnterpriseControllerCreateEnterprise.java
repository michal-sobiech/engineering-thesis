package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.CreateEnterpriseRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@Component
@RequiredArgsConstructor
public class EnterpriseControllerCreateEnterprise {

    private final EntrepreneurService entrepreneurService;
    private final EnterpriseService enterpriseService;

    public ResponseEntity<CreateEnterpriseResponse> createEnterprise(CreateEnterpriseRequest createEnterpriseRequest) {
        Optional<Long> optionalUserId = AuthUtils.getAuthPrincipal();
        if (optionalUserId.isEmpty()) {
            return HttpUtils.createUnauthorizedResponse();
        }
        long userId = optionalUserId.get();

        Optional<Entrepreneur> optionalEntrepreneur = entrepreneurService.findByUserId(userId);
        if (optionalEntrepreneur.isEmpty()) {
            return HttpUtils.createForbiddenResponse();
        }
        Entrepreneur entrepreneur = optionalEntrepreneur.get();

        final Enterprise enterprise = enterpriseService.createEnterprise(
                entrepreneur.getEntrepreneurId(),
                createEnterpriseRequest);
        final var responseBody = new CreateEnterpriseResponse(enterprise.getEnterpriseId());
        return ResponseEntity.ok(responseBody);
    }

}
