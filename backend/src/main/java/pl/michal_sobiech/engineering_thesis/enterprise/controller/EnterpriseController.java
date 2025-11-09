package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.time.LocalTime;
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
import org.SwaggerCodeGenExample.model.Location;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.File;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.enterprise.PatchEnterpriseRequestDto;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.custom_appointments.CreateCustomAppointmentsEnterpriseServiceCommand;
import pl.michal_sobiech.engineering_thesis.enterprise_service.custom_appointments.CustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.custom_appointments.GetEnterpriseServiceCustomServiceResponseFactory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments.CreateNoCustomAppointmentsEnterpriseServiceCommand;
import pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments.GetEnterpriseServiceNonCustomServiceResponseFactory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments.NonCustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.custom_appointments.CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments.CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.utils.DayOfWeekUtils;
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

        public ResponseEntity<CreateEnterpriseResponse> createEnterprise(
                        String name,
                        String description,
                        String address,
                        Double longitude,
                        Double latitude,
                        MultipartFile logoFile,
                        MultipartFile backgroundPhotoFile) {
                var command = new CreateEnterpriseCommand(
                                name,
                                description,
                                new Location(address, longitude, latitude),
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
                responseBody = responseBody.location(enterprise.location().orElse(null));

                return ResponseEntity.ok(responseBody);
        }

        @Override
        public ResponseEntity<List<GetEnterpriseEmployeesResponseItem>> getEnterpriseEmployees(Long enterpriseId) {
                return HttpUtils.createInternalServerErrorResponse();
        }

        @Override
        public ResponseEntity<List<GetEnterpriseService200Response>> getEnterpriseServices(Long enterpriseId) {

                List<GetEnterpriseServiceNonCustomServiceResponse> nonCustomServices = nonCustomAppointmentsEnterpriseServiceService
                                .getByEnterpriseId(enterpriseId)
                                .stream()
                                .map(GetEnterpriseServiceNonCustomServiceResponseFactory::fromDomain)
                                .collect(Collectors.toList());

                List<GetEnterpriseServiceCustomServiceResponse> customServices = customAppointmentsEnterpriseServiceService
                                .getByEnterpriseId(enterpriseId)
                                .stream()
                                .map(GetEnterpriseServiceCustomServiceResponseFactory::fromDomain)
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
                        Location location,
                        String timeZone,
                        Boolean takesCustomAppointments,
                        MultipartFile logoFile,
                        MultipartFile backgroundPhotoFile) {

                Entrepreneur entrepreneur = authService.requireEntrepreneur();

                Enterprise enterprise = enterpriseService.getEnterprise(enterpriseId).orElseThrow();

                if (enterprise.ownerUserId() != entrepreneur.getUserId()) {
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

        @Override
        public ResponseEntity<Void> createCustomAppointmentsEnterpriseService(
                        Long enterpriseId,
                        CreateCustomAppointmentsEnterpriseServiceRequest request) {
                List<CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand> createTimeWindowCommands = request
                                .getTimeWindows()
                                .stream()
                                .map(slot -> {
                                        LocalTime startTime = LocalTime.parse(slot.getStartTime());
                                        LocalTime endTime = LocalTime.parse(slot.getEndTime());
                                        return new CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand(
                                                        DayOfWeekUtils.swaggerToStdDayOfWeek(slot.getDayOfWeek()),
                                                        startTime,
                                                        endTime);
                                })
                                .collect(Collectors.toList());

                var command = new CreateCustomAppointmentsEnterpriseServiceCommand(
                                request.getName(),
                                request.getDescription(),
                                request.getLocation(),
                                ZoneId.of(request.getTimeZone()),
                                request.getMaxDistanceKm(),
                                EnterpriseServiceCathegory.valueOf(request.getCathegory()),
                                Optional.ofNullable(request.getPrice()),
                                CurrencyIso.valueOf(request.getCurrency()));

                customAppointmentsEnterpriseServiceService.saveWithTimeWindows(enterpriseId, command,
                                createTimeWindowCommands);
                return ResponseEntity.ok().build();
        }

        @Override
        public ResponseEntity<Void> createNoCustomAppointmentsEnterpriseService(
                        Long enterpriseId,
                        CreateNoCustomAppointmentsEnterpriseServiceRequest request) {
                List<CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand> createSlotCommands = request
                                .getSlots()
                                .stream()
                                .map(slot -> {
                                        LocalTime startTime = LocalTime.parse(slot.getStartTime());
                                        LocalTime endTime = LocalTime.parse(slot.getEndTime());
                                        return new CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand(
                                                        DayOfWeekUtils.swaggerToStdDayOfWeek(slot.getDayOfWeek()),
                                                        startTime,
                                                        endTime,
                                                        slot.getMaxOccupancy().shortValue());
                                })
                                .collect(Collectors.toList());

                var command = new CreateNoCustomAppointmentsEnterpriseServiceCommand(
                                request.getName(),
                                request.getDescription(),
                                request.getLocation(),
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
                                        .contentType(MediaType.IMAGE_PNG)
                                        .body(body);
                } else {
                        return ResponseEntity.noContent().build();
                }

        }

}
