package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.EnterprisesApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateCustomAppointmentsEnterpriseServiceRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.SwaggerCodeGenExample.model.CreateNoCustomAppointmentsEnterpriseServiceRequest;
import org.SwaggerCodeGenExample.model.GetEnterpriseEmployeesResponseItem;
import org.SwaggerCodeGenExample.model.GetEnterpriseResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseService200Response;
import org.SwaggerCodeGenExample.model.GetEnterpriseServiceCustomServiceResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseServiceNonCustomServiceResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.employee.EmployeeService;
import pl.michal_sobiech.core.enterprise.Enterprise;
import pl.michal_sobiech.core.enterprise.EnterpriseService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CreateCustomAppointmentsEnterpriseServiceCommand;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.CreateNoCustomAppointmentsEnterpriseServiceCommand;
import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.NonCustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CreateTimeWindowTemplateCommand;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomSlotTemplate;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomTimeWindowTemplateService;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.CreateSlotTemplateCommand;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplate;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplateService;
import pl.michal_sobiech.core.entrepreneur.Entrepreneur;
import pl.michal_sobiech.core.location.Location;
import pl.michal_sobiech.core.model.File;
import pl.michal_sobiech.engineering_thesis.api.LocationMapper;
import pl.michal_sobiech.engineering_thesis.api.SlotMapper;
import pl.michal_sobiech.engineering_thesis.api.TimeWindowMapper;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.GetEnterpriseServiceCustomServiceResponseFactory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.GetEnterpriseServiceNonCustomServiceResponseFactory;
import pl.michal_sobiech.engineering_thesis.file.FileMapper;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterprisesApi {

    private final AuthService authService;
    private final EnterpriseService enterpriseService;
    private final EmployeeService employeeService;
    private final NonCustomAppointmentsEnterpriseServiceService nonCustomAppointmentsEnterpriseServiceService;
    private final CustomAppointmentsEnterpriseServiceService customAppointmentsEnterpriseServiceService;

    private final EnterpriseControllerCreateEnterprise enterpriseControllerCreateEnterprise;
    private final EnterpriseControllerCreateEmployee enterpriseControllerCreateEmployee;

    private final CustomTimeWindowTemplateService customTimeWindowTemplateService;
    private final NonCustomSlotTemplateService nonCustomSlotTemplateService;

    public ResponseEntity<CreateEnterpriseResponse> createEnterprise(
            String name,
            String description,
            String address,
            Double longitude,
            Double latitude,
            MultipartFile logoFile,
            MultipartFile backgroundPhotoFile) {
        Location domainLocation = new Location(address, longitude, latitude);

        var command = new CreateEnterpriseCommand(
                name,
                description,
                domainLocation,
                Optional.ofNullable(logoFile),
                Optional.ofNullable(backgroundPhotoFile));

        return enterpriseControllerCreateEnterprise.createEnterprise(command);
    }

    @Override
    public ResponseEntity<CheckIndependentEndUserEmailExists200Response> checkEmployeeUsernameExists(
            Long enterpriseId, String username) {
        final boolean exists = employeeService.checkEmployeeUsernameExists(enterpriseId, username);
        final var responseBody = new CheckIndependentEndUserEmailExists200Response(exists);
        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<CreateEnterpriseEmployeeResponse> createEnterpriseEmployee(
            Long enterpriseId,
            CreateEnterpriseEmployeeRequest createEnterpriseEmployeeRequest) {
        return enterpriseControllerCreateEmployee.createEnterpriseEmployee(enterpriseId,
                createEnterpriseEmployeeRequest);
    }

    @Override
    public ResponseEntity<GetEnterpriseResponse> getEnterprise(Long enterpriseId) {
        Enterprise enterprise = enterpriseService.getEnterprise(enterpriseId).orElseThrow();

        var responseBody = new GetEnterpriseResponse(
                enterprise.name(),
                enterprise.description());

        org.SwaggerCodeGenExample.model.Location swaggerLocation = enterprise.location().map(LocationMapper::fromDomain)
                .orElse(null);
        responseBody = responseBody.location(swaggerLocation);

        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<List<GetEnterpriseEmployeesResponseItem>> getEnterpriseEmployees(Long enterpriseId) {
        var body = employeeService.getEnterpriseEmployeesByEntepriseId(enterpriseId)
                .stream()
                .map(employee -> new GetEnterpriseEmployeesResponseItem(employee.getUsername(), employee.getFirstName(),
                        employee.getLastName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetEnterpriseService200Response>> getEnterpriseServices(Long enterpriseId) {

        Enterprise enterprise = enterpriseService.getById(enterpriseId).orElseThrow();

        List<GetEnterpriseServiceNonCustomServiceResponse> nonCustomServices = nonCustomAppointmentsEnterpriseServiceService
                .getByEnterpriseId(enterpriseId)
                .stream()
                .map(service -> {
                    List<NonCustomSlotTemplate> slots = nonCustomSlotTemplateService
                            .getAllInWeekByEnterpriseServiceId(service.enterpriseServiceId());
                    return GetEnterpriseServiceNonCustomServiceResponseFactory.fromDomain(service, enterprise, slots);
                })
                .collect(Collectors.toList());

        List<GetEnterpriseServiceCustomServiceResponse> customServices = customAppointmentsEnterpriseServiceService
                .getByEnterpriseId(enterpriseId)
                .stream()
                .map(service -> {
                    List<CustomSlotTemplate> timeWindows = customTimeWindowTemplateService
                            .getAllInWeekByEnterpriseServiceId(service.enterpriseServiceId());
                    return GetEnterpriseServiceCustomServiceResponseFactory.fromDomain(service, enterprise,
                            timeWindows);
                })
                .collect(Collectors.toList());

        List<GetEnterpriseService200Response> body = new ArrayList<>();
        body.addAll(nonCustomServices);
        body.addAll(customServices);

        return ResponseEntity.ok(body);
    }

    // TODO cache invalidation
    @Override
    public ResponseEntity<Void> patchEnterprise(
            Long enterpriseId,
            String name,
            String description,
            org.SwaggerCodeGenExample.model.Location swaggerLocation,
            MultipartFile logoFile,
            MultipartFile backgroundPhotoFile) {

        Entrepreneur entrepreneur = authService.requireEntrepreneur();

        Enterprise enterprise = enterpriseService.getEnterprise(enterpriseId).orElseThrow();

        if (enterprise.ownerUserId() != entrepreneur.getUserId()) {
            return HttpUtils.createForbiddenResponse();
        }

        Location location = LocationMapper.fromSwagger(swaggerLocation);

        enterpriseService.patchEnterprise(
                enterpriseId.longValue(),
                Optional.ofNullable(name),
                Optional.ofNullable(description),
                Optional.ofNullable(location),
                Optional.ofNullable(logoFile).map(FileMapper::fromMultipartFile),
                Optional.ofNullable(backgroundPhotoFile).map(FileMapper::fromMultipartFile));

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createCustomAppointmentsEnterpriseService(
            Long enterpriseId,
            CreateCustomAppointmentsEnterpriseServiceRequest request) {
        List<CreateTimeWindowTemplateCommand> createTimeWindowCommands = request
                .getTimeWindows()
                .stream()
                .map(TimeWindowMapper::createCommandFromSwaggerTimeWindow)
                .collect(Collectors.toList());

        Location domainLocation = LocationMapper.fromSwagger(request.getLocation());

        var command = new CreateCustomAppointmentsEnterpriseServiceCommand(
                request.getName(),
                request.getDescription(),
                domainLocation,
                ZoneId.of(request.getTimeZone()),
                request.getMaxDistanceKm(),
                EnterpriseServiceCathegory.valueOf(request.getCathegory()),
                request.getPrice(),
                CurrencyIso.valueOf(request.getCurrency()));

        customAppointmentsEnterpriseServiceService.saveWithTimeWindows(enterpriseId, command,
                createTimeWindowCommands);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createNoCustomAppointmentsEnterpriseService(
            Long enterpriseId,
            CreateNoCustomAppointmentsEnterpriseServiceRequest request) {
        List<CreateSlotTemplateCommand> createSlotCommands = request
                .getSlots()
                .stream()
                .map(SlotMapper::fromSwaggerSlot)
                .collect(Collectors.toList());

        Location domainLocation = LocationMapper.fromSwagger(request.getLocation());

        var command = new CreateNoCustomAppointmentsEnterpriseServiceCommand(
                request.getName(),
                request.getDescription(),
                domainLocation,
                ZoneId.of(request.getTimeZone()),
                EnterpriseServiceCathegory.valueOf(request.getCathegory()),
                Optional.ofNullable(request.getPrice()),
                CurrencyIso.valueOf(request.getCurrency()));

        nonCustomAppointmentsEnterpriseServiceService.saveWithSlotTemplates(enterpriseId, command,
                createSlotCommands);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Resource> getEnterpriseLogoPhoto(Long enterpriseId) {
        Enterprise enterprise = enterpriseService.getEnterprise(enterpriseId).orElseThrow();

        if (enterprise.logo().isPresent()) {
            File logo = enterprise.logo().get();

            String contentDispositionHeader = "inline; filename=\"%s\"".formatted(logo.name());

            Resource body = new ByteArrayResource(logo.bytes(), logo.name());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionHeader)
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(body);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<Resource> getEnterpriseBackgroundPhoto(Long enterpriseId) {
        Enterprise enterprise = enterpriseService.getEnterprise(enterpriseId).orElseThrow();

        if (enterprise.backgroundPhoto().isPresent()) {
            File backgroundPhoto = enterprise.backgroundPhoto().get();

            String contentDispositionHeader = "inline; filename=\"%s\"".formatted(backgroundPhoto.name());

            Resource body = new ByteArrayResource(backgroundPhoto.bytes(), backgroundPhoto.name());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionHeader)
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(body);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

}
