package pl.michal_sobiech.engineering_thesis.employee;

import org.SwaggerCodeGenExample.api.EnterpriseEmployeeApi;
import org.SwaggerCodeGenExample.model.EmployeeGetMeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EnterpriseEmployeeApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<EmployeeGetMeResponse> getMeEmployee() {
        UserDomain user = authService.requireAuthorizedUser();
        EmployeeDomain employee = EmployeeDomain.fromUserDomain(user);

        final var responseBody = new EmployeeGetMeResponse(
                employee.getUserId(),
                employee.getEnterpriseId(),
                employee.getUsername(),
                employee.getFirstName(),
                employee.getLastName());

        return ResponseEntity.ok(responseBody);
    }

}
