package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.util.Optional;

import org.SwaggerCodeGenExample.api.EnterprisesApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterprisesApi {

    private final EnterpriseService enterpriseService;
    private final EmployeeService employeeService;

    private final EnterpriseControllerCreateEnterprise enterpriseControllerCreateEnterprise;
    private final EnterpriseControllerCreateEmployee enterpriseControllerCreateEmployee;

    @Override
    public ResponseEntity<CreateEnterpriseResponse> createEnterprise(CreateEnterpriseRequest createEnterpriseRequest) {
        return enterpriseControllerCreateEnterprise.createEnterprise(createEnterpriseRequest);
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
        return enterpriseControllerCreateEmployee.createEnterpriseEmployee(enterpriseId,
                createEnterpriseEmployeeRequest);
    }

    @Override
    public ResponseEntity<GetEnterpriseResponse> getEnterprise(Integer enterpriseId) {

        final Optional<Enterprise> optionalEnterprise = enterpriseService.findByEnterpriseId(enterpriseId);
        if (optionalEnterprise.isEmpty()) {
            return HttpUtils.createNotFoundReponse();
        }
        Enterprise enterprise = optionalEnterprise.get();

        final var responseBody = new GetEnterpriseResponse(
                enterprise.getName(),
                enterprise.getDescription(),
                enterprise.getLocation());

        return ResponseEntity.ok(responseBody);
    }

}
