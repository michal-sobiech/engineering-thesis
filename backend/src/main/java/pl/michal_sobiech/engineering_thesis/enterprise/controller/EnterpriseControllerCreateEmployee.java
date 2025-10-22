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
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.engineering_thesis.jwt.JwtCreationService;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@Component
@RequiredArgsConstructor
public class EnterpriseControllerCreateEmployee {

    private final AuthService authService;
    private final EntrepreneurService entrepreneurService;
    private final EnterpriseService enterpriseService;
    private final JwtCreationService jwtCreationService;
    private final EmployeeService employeeService;

    public ResponseEntity<CreateEnterpriseEmployeeResponse> createEnterpriseEmployee(
            Long enterpriseId,
            CreateEnterpriseEmployeeRequest request) {
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
        String newEmployeeUsername = request.getUsername();
        if (employeeService.checkEmployeeUsernameExists(enterpriseId, newEmployeeUsername)) {
            return HttpUtils.createConflictResponse();
        }

        final String password = request.getPassword();
        final EmployeeDomain employee = employeeService.save(
                enterpriseId,
                request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                password);

        final CreateEnterpriseEmployeeResponseUser responseBodyUser = new CreateEnterpriseEmployeeResponseUser(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUsername());

        final var responseBody = new CreateEnterpriseEmployeeResponse(responseBodyUser);
        return ResponseEntity.ok(responseBody);
    }

}
