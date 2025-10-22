package pl.michal_sobiech.engineering_thesis.enterprise.controller;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import org.SwaggerCodeGenExample.api.EnterprisesApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseResponse;
import org.SwaggerCodeGenExample.model.CreateEnterpriseServiceRequest;
import org.SwaggerCodeGenExample.model.GetEnterpriseEmployeesResponseItem;
import org.SwaggerCodeGenExample.model.GetEnterpriseResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseServicesResponseItem;
import org.SwaggerCodeGenExample.model.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.enterprise.PatchEnterpriseRequestDto;
import pl.michal_sobiech.engineering_thesis.enterprise_service.CreateEnterpriseServiceCommand;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.CreateEnterpriseServiceSlotCommand;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.user.UserService;
import pl.michal_sobiech.engineering_thesis.utils.DayOfWeekUtils;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;
import pl.michal_sobiech.engineering_thesis.utils.JsonNullableUtils;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterprisesApi {

    private final AuthService authService;
    private final EnterpriseService enterpriseService;
    private final EnterpriseServiceService enterpriseServiceService;
    private final UserService userService;

    private final EnterpriseControllerCreateEnterprise enterpriseControllerCreateEnterprise;
    private final EnterpriseControllerCreateEmployee enterpriseControllerCreateEmployee;

    @Override
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
            Integer enterpriseId, String username) {
        final boolean exists = userService.checkEmployeeUsernameExists(enterpriseId, username);
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

        Location location = new Location(
                enterprise.getAddress(),
                enterprise.getLongitude(),
                enterprise.getLatitude());

        var responseBody = new GetEnterpriseResponse(
                enterprise.getName(),
                enterprise.getDescription(),
                location);

        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<List<GetEnterpriseEmployeesResponseItem>> getEnterpriseEmployees(Integer enterpriseId) {
        return HttpUtils.createInternalServerErrorResponse();
    }

    @Override
    public ResponseEntity<List<GetEnterpriseServicesResponseItem>> getEnterpriseServices(Integer enterpriseId) {
        var services = enterpriseServiceService.findByEnterpriseId(enterpriseId);
        var body = services.stream()
                .map(service -> new GetEnterpriseServicesResponseItem(
                        service.getEnterpriseServiceId(),
                        service.getName(),
                        service.getDescription()))
                .toList();
        return ResponseEntity.ok(body);
    }

    // TODO cache invalidation
    @Override
    public ResponseEntity<Void> patchEnterprise(
            Long enterpriseId,
            String name,
            String description,
            Location location,
            MultipartFile logoFile,
            MultipartFile backgroundPhotoFile) {

        Entrepreneur entrepreneur = authService.requireEntrepreneur();

        Enterprise enterprise = enterpriseService.findByEnterpriseId(enterpriseId).orElseThrow();

        if (enterprise.getOwnerUserId() != entrepreneur.getUserId()) {
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
    public ResponseEntity<Void> createEnterpriseService(
            Integer enterpriseId,
            CreateEnterpriseServiceRequest createEnterpriseService) {

        List<CreateEnterpriseServiceSlotCommand> createSlotCommands = createEnterpriseService.getSlots()
                .stream()
                .map(slot -> new CreateEnterpriseServiceSlotCommand(
                        DayOfWeekUtils.swaggerToStdDayOfWeek(slot.getDayOfWeek()),
                        LocalTime.parse(slot.getStart()),
                        LocalTime.parse(slot.getEnd())))
                .toList();

        CreateEnterpriseServiceCommand command = new CreateEnterpriseServiceCommand(
                createEnterpriseService.getName(),
                createEnterpriseService.getDescription(),
                JsonNullableUtils.jsonNullableToOptional(createEnterpriseService.getLocation()),
                ZoneId.of(createEnterpriseService.getTimeZone()),
                createSlotCommands,
                createEnterpriseService.getTakesCustomAppointments(),
                createEnterpriseService.getPrice(),
                Currency.getInstance(createEnterpriseService.getCurrency()));

        enterpriseServiceService.save(enterpriseId, command);
        return ResponseEntity.ok().build();
    }

}
