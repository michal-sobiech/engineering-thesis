package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.util.List;
import java.util.Optional;

import org.SwaggerCodeGenExample.api.EnterprisesApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseEmployeesResponseItem;
import org.SwaggerCodeGenExample.model.GetEnterpriseResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseServicesResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.auth.EntrepreneurAuthContext;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.enterprise.PatchEnterpriseRequestDto;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterprisesApi {

    private final AuthService authService;
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
                enterprise.getLocation(),
                enterprise.getLogoPhotoId(),
                enterprise.getBackgroundPhotoId());

        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<List<GetEnterpriseEmployeesResponseItem>> getEnterpriseEmployees(Integer enterpriseId) {
        return HttpUtils.createInternalServerErrorResponse();
    }

    @Override
    public ResponseEntity<List<GetEnterpriseServicesResponseItem>> getEnterpriseServices(Integer enterpriseId) {
        return HttpUtils.createInternalServerErrorResponse();
    }

    // TODO cache invalidation
    @Override
    public ResponseEntity<Void> patchEnterprise(
            Long enterpriseId,
            String name,
            String description,
            String location,
            MultipartFile logoFile,
            MultipartFile backgroundPhotoFile) {

        EntrepreneurAuthContext entrepreneurAuthContext = authService.requireEntrepreneur();
        Entrepreneur entrepreneur = entrepreneurAuthContext.entrepreneur();

        Enterprise enterprise = enterpriseService.findByEnterpriseId(enterpriseId).orElseThrow();

        if (enterprise.getEntrepreneurId() != entrepreneur.getEntrepreneurId()) {
            return HttpUtils.createForbiddenResponse();
        }

        PatchEnterpriseRequestDto requestDto = new PatchEnterpriseRequestDto(
                enterpriseId,
                Optional.ofNullable(name),
                Optional.ofNullable(description),
                Optional.ofNullable(location),
                Optional.ofNullable(logoFile),
                Optional.ofNullable(backgroundPhotoFile));
        enterpriseService.patchEnterprise(requestDto);

        return ResponseEntity.ok().build();
    }

}
