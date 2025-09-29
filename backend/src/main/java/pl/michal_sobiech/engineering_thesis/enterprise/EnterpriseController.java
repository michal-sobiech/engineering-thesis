package pl.michal_sobiech.engineering_thesis.enterprise;

import java.math.BigDecimal;

import org.SwaggerCodeGenExample.api.EnterprisesApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponseUser;
import org.SwaggerCodeGenExample.model.CreateEnterpriseRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.Employee;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.engineering_thesis.jwt.JwtCreationService;
import pl.michal_sobiech.engineering_thesis.user.AuthPrincipal;
import pl.michal_sobiech.engineering_thesis.user.Role;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterprisesApi {

    private final EnterpriseService enterpriseService;
    private final EntrepreneurService entrepreneurService;
    private final EmployeeService employeeService;
    private final JwtCreationService jwtCreationService;

    @Override
    public ResponseEntity<CreateEnterpriseResponse> createEnterprise(CreateEnterpriseRequest createEnterpriseRequest) {
        final AuthPrincipal authPrincipal = AuthUtils.getAuthPrincipal();
        if (authPrincipal.role() != Role.ENTREPRENEUR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        final Enterprise enterprise = enterpriseService.createEnterprise(
                authPrincipal.userId(),
                createEnterpriseRequest);
        final BigDecimal enterpriseId = BigDecimal.valueOf(enterprise.getEnterpriseId());
        final var responseBody = new CreateEnterpriseResponse(enterpriseId);
        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<CheckIndependentEndUserEmailExists200Response> checkEmployeeUsernameExists(
            Integer enterpriseId, String username) {
        final boolean exists = employeeService.checkEmployeeUsernameExists(enterpriseId, username);
        final var responseBody = new CheckIndependentEndUserEmailExists200Response(exists);
        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<CreateEnterpriseEmployeeResponse> createEnterpriseEmployee(
            Integer enterpriseId,
            CreateEnterpriseEmployeeRequest createEnterpriseEmployeeRequest) {
        final AuthPrincipal authPrincipal = AuthUtils.getAuthPrincipal();
        if (authPrincipal.role() != Role.ENTREPRENEUR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        final String password = createEnterpriseEmployeeRequest.getPassword();

        final Employee employee = employeeService.createEmployee(
                enterpriseId,
                createEnterpriseEmployeeRequest.getFirstName(),
                createEnterpriseEmployeeRequest.getLastName(),
                password,
                createEnterpriseEmployeeRequest.getUsername());

        final var responseBodyUser = new CreateEnterpriseEmployeeResponseUser(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUsername());

        final long newEmployeeUserId = employee.getUserId();
        final String newEmployeeUserIdAsStr = Long.toString(newEmployeeUserId);
        final String accessToken = jwtCreationService.generateTokenNow(newEmployeeUserIdAsStr);

        final var responseBody = new CreateEnterpriseEmployeeResponse(accessToken, responseBodyUser);
        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<GetEnterpriseResponse> getEnterprise(int enterpriseId) {

    }

}
