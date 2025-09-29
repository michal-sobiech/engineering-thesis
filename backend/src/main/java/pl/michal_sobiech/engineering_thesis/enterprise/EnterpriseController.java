package pl.michal_sobiech.engineering_thesis.enterprise;

import java.math.BigDecimal;
import java.util.Optional;

import org.SwaggerCodeGenExample.api.EnterprisesApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.engineering_thesis.user.auth_principal.AuthPrincipal;
import pl.michal_sobiech.engineering_thesis.user.auth_principal.EntrepreneurAuthPrincipal;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterprisesApi {

    private final EnterpriseService enterpriseService;
    private final EntrepreneurService entrepreneurService;
    private final EmployeeService employeeService;

    ResponseEntity<CreateEnterpriseResponse> createEnterprise(
            CreateEnterpriseRequest createEnterpriseRequest,
            @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        if (!(authPrincipal instanceof EntrepreneurAuthPrincipal entrepreneurAuthPrincipal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        final long entrepreneurId = entrepreneurAuthPrincipal.entrepreneurId();
        final Optional<Entrepreneur> entrepreneur = entrepreneurService.getEnrepreneur(entrepreneurId);
        if (entrepreneur.isEmpty()) {
            final String message = "Entrepreneur id from the Security Context does not point to any existing entrepreneur";
            throw new IllegalStateException(message);
        }

        final Enterprise enterprise = enterpriseService.createEnterprise(entrepreneurId, createEnterpriseRequest);
        final BigDecimal enterpriseId = BigDecimal.valueOf(enterprise.getEnterpriseId());
        final var responseBody = new CreateEnterpriseResponse(enterpriseId);
        return ResponseEntity.ok(responseBody);
    }

    ResponseEntity<CheckIndependentEndUserEmailExists200Response> checkEmployeeUsernameExists(
            Integer enterpriseId,
            String username,
            @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        final boolean exists = employeeService.checkEmployeeUsernameExists(enterpriseId, username);
        final var responseBody = new CheckIndependentEndUserEmailExists200Response(exists);
        return ResponseEntity.ok(responseBody);
    }

    ResponseEntity<CreateEnterpriseEmployeeResponse> createEnterpriseEmployee(
            Integer enterpriseId,
            CreateEnterpriseEmployeeRequest createEnterpriseEmployeeRequest,
            @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        if (!(authPrincipal instanceof EntrepreneurAuthPrincipal entrepreneurAuthPrincipal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return employeeService.sa
            
    }

    ResponseEntity<GetEnterpriseResponse> getEnterprise(
            Integer enterpriseId);

}
