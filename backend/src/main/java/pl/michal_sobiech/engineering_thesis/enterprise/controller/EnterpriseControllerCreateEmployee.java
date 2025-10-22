package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponseUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeDomain;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.engineering_thesis.jwt.JwtCreationService;
import pl.michal_sobiech.engineering_thesis.user.UserService;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@Component
@RequiredArgsConstructor
public class EnterpriseControllerCreateEmployee {

    private final AuthService authService;
    private final EntrepreneurService entrepreneurService;
    private final EnterpriseService enterpriseService;
    private final JwtCreationService jwtCreationService;
    private final UserService userService;

    public ResponseEntity<CreateEnterpriseEmployeeResponse> createEnterpriseEmployee(
            Integer enterpriseId,
            CreateEnterpriseEmployeeRequest createEnterpriseEmployeeRequest) {
        Entrepreneur entrepreneur = authService.requireEntrepreneur();

        Optional<Enterprise> optionalEnterprise = enterpriseService.findByEnterpriseId(enterpriseId);
        if (optionalEnterprise.isEmpty()) {
            return HttpUtils.createNotFoundReponse();
        }
        Enterprise enterprise = optionalEnterprise.get();

        if (entrepreneur.getUserId() != enterprise.getOwnerUserId()) {
            return HttpUtils.createForbiddenResponse();
        }

        // TODO A race might take place here
        String newEmployeeUsername = createEnterpriseEmployeeRequest.getUsername();
        if (userService.checkEmployeeUsernameExists(enterpriseId, newEmployeeUsername)) {
            return HttpUtils.createConflictResponse();
        }

        final String password = createEnterpriseEmployeeRequest.getPassword();
        final EmployeeDomain employee = userService.createEmployee(
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

}
