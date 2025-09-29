package pl.michal_sobiech.engineering_thesis.employee;

import java.util.Optional;

import org.SwaggerCodeGenExample.api.EnterpriseEmployeeApi;
import org.SwaggerCodeGenExample.model.EmployeeGetMeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.auth_principal.AuthPrincipal;
import pl.michal_sobiech.engineering_thesis.user.auth_principal.EmployeeAuthPrincipal;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EnterpriseEmployeeApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeGetMeResponse> getMe() {
        final Optional<AuthPrincipal> authPrincipalOptional = AuthUtils.getAuthPrincipal();
        if (authPrincipalOptional.isEmpty()) {
            return HttpUtils.createUnauthorizedResponse();
        }

        AuthPrincipal authPrincipal = authPrincipalOptional.get();
        if (!(authPrincipal instanceof EmployeeAuthPrincipal employeeAuthPrincipal)) {
            return HttpUtils.createForbiddenResponse();
        }

        final Employee employee = employeeService.getEmployee(employeeAuthPrincipal.getEmployeeId());

        final var responseBody = new EmployeeGetMeResponse(
                employee.getUserId(),
                employee.getEmployeeId(),
                employee.getEnterpriseId(),
                employee.getUsername(),
                employee.getFirstName(),
                employee.getLastName());

        return ResponseEntity.ok(responseBody);
    }

}
