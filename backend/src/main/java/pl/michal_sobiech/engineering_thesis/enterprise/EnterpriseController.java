package pl.michal_sobiech.engineering_thesis.enterprise;

import org.SwaggerCodeGenExample.api.EnterprisesApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnterpriseController implements EnterprisesApi {

    private final EnterpriseService service;

    ResponseEntity<CreateEnterpriseResponse> createEnterprise(
            CreateEnterpriseRequest createEnterpriseRequest,
            @AuthenticationPrincipal Jwt jwt     
    ) {
        final Enterprise enterprise = Enterprise.builder()
                .enterpreneurId(createEnterpriseRequest.en)
        service.sav
    }

    ResponseEntity<CheckIndependentEndUserEmailExists200Response> checkEmployeeUsernameExists(Integer enterpriseId, String username) {
        service.c
    }

    ResponseEntity<CreateEnterpriseEmployeeResponse> createEnterpriseEmployee(
            Integer enterpriseId,
            CreateEnterpriseEmployeeRequest createEnterpriseEmployeeRequest);

    ResponseEntity<GetEnterpriseResponse> getEnterprise(
            Integer enterpriseId);

}
