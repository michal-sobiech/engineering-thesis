package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponseUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.Employee;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserService;
import pl.michal_sobiech.engineering_thesis.jwt.JwtCreationService;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@Component
@RequiredArgsConstructor
public class EnterpriseControllerCreateEmployee {

    private final EntrepreneurService entrepreneurService;
    private final EnterpriseService enterpriseService;
    private final EmployeeService employeeService;
    private final JwtCreationService jwtCreationService;
    private final IndependentEndUserService independentEndUserService;

    public ResponseEntity<CreateEnterpriseEmployeeResponse> createEnterpriseEmployee(
            Integer enterpriseId,
            CreateEnterpriseEmployeeRequest createEnterpriseEmployeeRequest) {

        Optional<Long> optionalUserId = AuthUtils.getAuthPrincipal();
        if (optionalUserId.isEmpty()) {
            return HttpUtils.createUnauthorizedResponse();
        }
        long userId = optionalUserId.get();

        Optional<IndependentEndUser> optionalIndependentEndUser = independentEndUserService.findByUserId(userId);
        if (optionalIndependentEndUser.isEmpty()) {
            return HttpUtils.createForbiddenResponse();
        }
        IndependentEndUser independentEndUser = optionalIndependentEndUser.get();

        long independentEndUserId = independentEndUser.getIndependentEndUserId();
        Optional<Entrepreneur> optionalEntrepreneur = entrepreneurService
                .findByIndependentEndUserId(independentEndUserId);
        if (optionalEntrepreneur.isEmpty()) {
            return HttpUtils.createForbiddenResponse();
        }
        Entrepreneur entrepreneur = optionalEntrepreneur.get();

        Optional<Enterprise> optionalEnterprise = enterpriseService.findByEnterpriseId(enterpriseId);
        if (optionalEnterprise.isEmpty()) {
            return HttpUtils.createNotFoundReponse();
        }
        Enterprise enterprise = optionalEnterprise.get();

        if (entrepreneur.getEntrepreneurId() != enterprise.getEntrepreneurId()) {
            return HttpUtils.createForbiddenResponse();
        }

        // TODO A race might take place here
        String newEmployeeUsername = createEnterpriseEmployeeRequest.getUsername();
        if (employeeService.existsByEnterpriseIdAndUsername(enterpriseId, newEmployeeUsername)) {
            return HttpUtils.createConflictResponse();
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

}
