package pl.michal_sobiech.engineering_thesis.enterprise;

import java.util.Optional;

import org.SwaggerCodeGenExample.api.EnterprisesApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponseUser;
import org.SwaggerCodeGenExample.model.CreateEnterpriseRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.Employee;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.engineering_thesis.jwt.JwtCreationService;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterprisesApi {

    private final EnterpriseService enterpriseService;
    private final EntrepreneurService entrepreneurService;
    private final EmployeeService employeeService;
    private final JwtCreationService jwtCreationService;

    @Override
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

    @Override
    public ResponseEntity<GetEnterpriseResponse> getEnterprise(Integer enterpriseId) {

        final Enterprise enterprise = enterpriseService.getEnterprise(enterpriseId);

        final var responseBody = new GetEnterpriseResponse(
                enterprise.getName(),
                enterprise.getDescription(),
                enterprise.getLocation());

        return ResponseEntity.ok(responseBody);
    }

}
