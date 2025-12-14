package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.ServicesApi;
import org.SwaggerCodeGenExample.model.CreateCustomAppointmentRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseServiceReviewRequest;
import org.SwaggerCodeGenExample.model.CreateNonCustomAppointmentRequest;
import org.SwaggerCodeGenExample.model.GetEnterpriseService200Response;
import org.SwaggerCodeGenExample.model.GetEnterpriseServiceCustomServiceResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseServiceNonCustomServiceResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseServicePendingAppointmentResponse;
import org.SwaggerCodeGenExample.model.GetEnterpriseServiceUncancelledFutureScheduledAppointmentResponse;
import org.SwaggerCodeGenExample.model.GetServiceCustomAppointmentsStatus200Response;
import org.SwaggerCodeGenExample.model.GetServiceFreeCustomAppointmentsResponseItem;
import org.SwaggerCodeGenExample.model.GetServiceFreeNonCustomAppointmentsResponseItem;
import org.SwaggerCodeGenExample.model.PatchCustomEnterpriseServiceRequest;
import org.SwaggerCodeGenExample.model.PatchNonCustomEnterpriseServiceRequest;
import org.SwaggerCodeGenExample.model.ServiceSearchResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.AppointmentWithDetailsService;
import pl.michal_sobiech.core.appointment.custom.CustomAppointmentService;
import pl.michal_sobiech.core.appointment.custom.CustomAppointmentWithDetailsService;
import pl.michal_sobiech.core.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.core.available_enterprise_service_search.AvailableEnterpriseServiceSearchResultRow;
import pl.michal_sobiech.core.available_enterprise_service_search.AvailableEnterpriseServiceSearchService;
import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.customer.Customer;
import pl.michal_sobiech.core.enterprise.Enterprise;
import pl.michal_sobiech.core.enterprise.EnterpriseService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomEnterpriseService;
import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.NonCustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.NonCustomEnterpriseService;
import pl.michal_sobiech.core.enterprise_service_availability.CustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.core.enterprise_service_availability.NonCustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CreateTimeWindowTemplateCommand;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomSlotTemplate;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomTimeWindowTemplateService;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.CreateSlotTemplateCommand;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplate;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplateService;
import pl.michal_sobiech.core.location.Location;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatusNotPaid;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatusPaidOnSite;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatusPaidOnline;
import pl.michal_sobiech.core.review.ReviewService;
import pl.michal_sobiech.core.utils.DateUtils;
import pl.michal_sobiech.core.utils.instant_window.InstantWindow;
import pl.michal_sobiech.engineering_thesis.api.LocationMapper;
import pl.michal_sobiech.engineering_thesis.api.SlotMapper;
import pl.michal_sobiech.engineering_thesis.api.TimeWindowMapper;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class EnterpriseServiceController implements ServicesApi {

    private static final double MAX_VALUE_OF_MAX_DISTANCE_KM = 100;
    private final EnterpriseServiceService enterpriseServiceService;
    private final CustomAppointmentsEnterpriseServiceService customAppointmentsEnterpriseServiceService;
    private final NonCustomAppointmentsEnterpriseServiceService nonCustomAppointmentsEnterpriseServiceService;
    private final CustomAppointmentService customAppointmentService;
    private final NonCustomAppointmentsService nonCustomAppointmentsService;
    private final AuthService authService;
    private final ReviewService reviewService;
    private final NonCustomEnterpriseServiceAvailabilityService nonCustomEnterpriseServiceAvailabilityService;
    private final CustomEnterpriseServiceAvailabilityService customEnterpriseServiceAvailabilityService;
    private final AvailableEnterpriseServiceSearchService availableEnterpriseServiceSearchService;
    private final AppointmentWithDetailsService appointmentWithDetailsService;
    private final CustomAppointmentWithDetailsService customAppointmentWithDetailsService;
    private final EnterpriseService enterpriseService;

    private final CustomTimeWindowTemplateService customTimeWindowTemplateService;
    private final NonCustomSlotTemplateService nonCustomSlotTemplateService;

    @Override
    public ResponseEntity<List<GetServiceFreeNonCustomAppointmentsResponseItem>> getFreeNonCustomAppointments(
            Long enterpriseServiceId,
            LocalDate dateInServiceTimezone) {
        ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(enterpriseServiceId);

        ZonedDateTime startZoned = dateInServiceTimezone.atStartOfDay(timezone);
        ZonedDateTime endZoned = startZoned.plusDays(1);

        Instant start = startZoned.toInstant();
        Instant end = endZoned.toInstant();

        List<InstantWindow> freeSlots = nonCustomEnterpriseServiceAvailabilityService
                .calcServiceAvailability(enterpriseServiceId, start, end);

        List<GetServiceFreeNonCustomAppointmentsResponseItem> body = freeSlots.stream().map(slot -> {
            LocalDateTime slotStartLocal = LocalDateTime.ofInstant(slot.start(), timezone);
            LocalDateTime slotEndLocal = LocalDateTime.ofInstant(slot.end(), timezone);

            String startTime = DateUtils.extractHHmmTimeFromLocalDateTime(slotStartLocal);
            String endTime = DateUtils.extractHHmmTimeFromLocalDateTime(slotEndLocal);

            return new GetServiceFreeNonCustomAppointmentsResponseItem(startTime, endTime);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetServiceFreeCustomAppointmentsResponseItem>> getFreeTimeWindowsForCustomAppointments(
            Long enterpriseServiceId,
            LocalDate dateInServiceTimezone) {
        ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(enterpriseServiceId);

        ZonedDateTime startZoned = dateInServiceTimezone.atStartOfDay(timezone);
        ZonedDateTime endZoned = startZoned.plusDays(1);

        Instant start = startZoned.toInstant();
        Instant end = endZoned.toInstant();

        List<InstantWindow> freeWindows = customEnterpriseServiceAvailabilityService
                .calcServiceAvailability(enterpriseServiceId, start, end);

        List<GetServiceFreeCustomAppointmentsResponseItem> body = freeWindows
                .stream()
                .map(slot -> {
                    LocalDateTime slotStartLocal = LocalDateTime.ofInstant(slot.start(), timezone);
                    LocalDateTime slotEndLocal = LocalDateTime.ofInstant(slot.end(), timezone);

                    String startTime = DateUtils.extractHHmmTimeFromLocalDateTime(slotStartLocal);
                    String endTime = DateUtils.extractHHmmTimeFromLocalDateTime(slotEndLocal);

                    return new GetServiceFreeCustomAppointmentsResponseItem(startTime, endTime);
                }).collect(Collectors.toList());

        return ResponseEntity.ok(body);

    }

    @Override
    public ResponseEntity<GetServiceCustomAppointmentsStatus200Response> getServiceCustomAppointmentsStatus(
            Long enterpriseServiceId) {
        boolean isCustom = enterpriseServiceService.isEnterpriseServiceCustom(enterpriseServiceId);
        var body = new GetServiceCustomAppointmentsStatus200Response(isCustom);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<ServiceSearchResponseItem>> searchServices(
            Double preferredLongitude,
            Double preferredLatitude,
            String cathegory,
            Double maxDistanceKm,
            @Nullable String serviceName,
            @Nullable String enterpriseName,
            @Nullable OffsetDateTime startDate,
            @Nullable OffsetDateTime endDate) {
        if (maxDistanceKm > MAX_VALUE_OF_MAX_DISTANCE_KM) {
            return HttpUtils.createBadRequestResponse();
        }

        Optional<EnterpriseServiceCathegory> optionalCathegoryEnum = Optional.ofNullable(
                EnterpriseServiceCathegory.enterpriseServiceCathegoryToString.inverse().get(cathegory));
        if (optionalCathegoryEnum.isEmpty()) {
            return HttpUtils.createBadRequestResponse();
        }
        EnterpriseServiceCathegory cathegoryEnum = optionalCathegoryEnum.get();

        List<AvailableEnterpriseServiceSearchResultRow> availableServices = availableEnterpriseServiceSearchService
                .searchAvailableServices(
                        preferredLongitude,
                        preferredLatitude,
                        maxDistanceKm,
                        cathegoryEnum,
                        Optional.ofNullable(serviceName),
                        Optional.ofNullable(enterpriseName),
                        startDate.toInstant(),
                        endDate.toInstant());

        List<ServiceSearchResponseItem> body = availableServices
                .stream()
                .map(service -> new ServiceSearchResponseItem(
                        service.enterpriseServiceId(),
                        service.enterpriseServiceName(),
                        service.enterpriseName(),
                        service.address(),
                        service.price()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createCustomAppointment(
            Long enterpriseServiceId,
            CreateCustomAppointmentRequest request) {
        Customer customer = authService.requireCustomer();

        // TODO check if appointment can be made

        ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(enterpriseServiceId);

        LocalDateTime startParsed = LocalDateTime.parse(request.getStartDatetimeEnterpriseServiceLocal());
        Instant startInstant = startParsed.atZone(timezone).toInstant();

        LocalDateTime endParsed = LocalDateTime.parse(request.getEndDatetimeEnterpriseServiceLocal());
        Instant endInstant = endParsed.atZone(timezone).toInstant();

        Location location = new Location(
                request.getLocation().getAddress(),
                request.getLocation().getLongitude(),
                request.getLocation().getLatitude());

        customAppointmentService.createCustomAppointment(
                enterpriseServiceId,
                customer.getUserId(),
                startInstant,
                endInstant,
                location);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createNonCustomAppointment(
            Long enterpriseServiceId,
            CreateNonCustomAppointmentRequest request) {
        Customer customer = authService.requireCustomer();

        ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(enterpriseServiceId);

        LocalDateTime startParsed = LocalDateTime.parse(request.getStartDatetimeEnterpriseServiceLocal());
        Instant startInstant = startParsed.atZone(timezone).toInstant();

        LocalDateTime endParsed = LocalDateTime.parse(request.getEndDatetimeEnterpriseServiceLocal());
        Instant endInstant = endParsed.atZone(timezone).toInstant();

        nonCustomAppointmentsService.createNonCustomAppointment(
                enterpriseServiceId,
                customer.getUserId(),
                startInstant,
                endInstant);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<GetEnterpriseService200Response> getEnterpriseService(Long enterpriseServiceId) {
        boolean isCustom = enterpriseServiceService.isEnterpriseServiceCustom(enterpriseServiceId);

        GetEnterpriseService200Response body;
        if (isCustom) {
            var service = customAppointmentsEnterpriseServiceService
                    .getByEnterpriseServiceId(enterpriseServiceId)
                    .orElseThrow();
            Enterprise enterprise = enterpriseService.getById(service.enterpriseId()).orElseThrow();
            List<CustomSlotTemplate> timeWindows = customTimeWindowTemplateService
                    .getAllInWeekByEnterpriseServiceId(service.enterpriseServiceId());
            body = GetEnterpriseServiceCustomServiceResponseFactory.fromDomain(service, enterprise, timeWindows);
        } else {
            var service = nonCustomAppointmentsEnterpriseServiceService
                    .getByEnterpriseServiceId(enterpriseServiceId)
                    .orElseThrow();
            Enterprise enterprise = enterpriseService.getById(service.enterpriseId()).orElseThrow();
            List<NonCustomSlotTemplate> slots = nonCustomSlotTemplateService
                    .getAllInWeekByEnterpriseServiceId(service.enterpriseServiceId());
            body = GetEnterpriseServiceNonCustomServiceResponseFactory.fromDomain(service, enterprise, slots);
        }
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createEnterpriseServiceReview(
            Long serviceId,
            CreateEnterpriseServiceReviewRequest request) {
        System.out.println("JJJJJJJJJJJJJJJJJ");
        Customer customer = authService.requireCustomer();

        System.out.println("IS A CUSTOMR");

        short numStarsOutOf5 = request.getNumStarsOutOf5().shortValue();
        if (numStarsOutOf5 < 1 || numStarsOutOf5 > 5) {
            throw new IllegalArgumentException();
        }

        reviewService.createReview(
                customer.getUserId(),
                serviceId,
                request.getNumStarsOutOf5().shortValue(),
                request.getText());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<GetEnterpriseServiceUncancelledFutureScheduledAppointmentResponse>> getEnterpriseServiceUncancelledFutureScheduledAppointments(
            Long enterpriseServiceId) {
        var body = appointmentWithDetailsService
                .getEnterpriseServiceUncancelledFutureScheduledAppointmentsWithDetails(
                        enterpriseServiceId)
                .stream()
                .map(appointment -> {
                    ZoneId timezone = enterpriseServiceService
                            .getTimeZoneByServiceId(appointment.enterpriseServiceId());

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.startInstant(),
                            timezone);
                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.endInstant(),
                            timezone);

                    boolean isPaid = switch (appointment.paymentStatus()) {
                        case PaymentStatusNotPaid _ -> false;
                        case PaymentStatusPaidOnSite _ -> true;
                        case PaymentStatusPaidOnline _ -> true;
                        default -> throw new IllegalArgumentException();
                    };

                    return new GetEnterpriseServiceUncancelledFutureScheduledAppointmentResponse(
                            appointment.appointmentId(),
                            appointment.customerUsername(),
                            appointment.customerFirstName(),
                            appointment.customerLastName(),
                            appointment.location().address(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price(),
                            appointment.currency().toString(),
                            isPaid);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetEnterpriseServicePendingAppointmentResponse>> getEnterpriseServicePendingAppointments(
            Long enterpriseServiceId) {
        var body = customAppointmentWithDetailsService
                .getEnterpriseServiceUncancelledPendingAppointmentsWithDetails(enterpriseServiceId)
                .stream()
                .map(appointment -> {
                    ZoneId timezone = enterpriseServiceService
                            .getTimeZoneByServiceId(appointment.enterpriseServiceId());

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.startInstant(),
                            timezone);
                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.endInstant(),
                            timezone);
                    return new GetEnterpriseServicePendingAppointmentResponse(
                            appointment.appointmentId(),
                            appointment.customerUsername(),
                            appointment.customerFirstName(),
                            appointment.customerLastName(),
                            appointment.location().address(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price(),
                            appointment.currency().toString());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<GetEnterpriseServiceCustomServiceResponse> getCustomEnterpriseService(Long serviceId) {
        CustomEnterpriseService service = customAppointmentsEnterpriseServiceService.getByEnterpriseServiceId(serviceId)
                .orElseThrow();
        Enterprise enterprise = enterpriseService.getById(service.enterpriseId()).orElseThrow();

        List<CustomSlotTemplate> timeWindows = customTimeWindowTemplateService
                .getAllInWeekByEnterpriseServiceId(serviceId);

        var body = GetEnterpriseServiceCustomServiceResponseFactory.fromDomain(service, enterprise, timeWindows);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<GetEnterpriseServiceNonCustomServiceResponse> getNonCustomEnterpriseService(Long serviceId) {
        NonCustomEnterpriseService service = nonCustomAppointmentsEnterpriseServiceService
                .getByEnterpriseServiceId(serviceId)
                .orElseThrow();
        Enterprise enterprise = enterpriseService.getById(service.enterpriseId()).orElseThrow();

        List<NonCustomSlotTemplate> slots = nonCustomSlotTemplateService.getAllInWeekByEnterpriseServiceId(serviceId);

        var body = GetEnterpriseServiceNonCustomServiceResponseFactory.fromDomain(service, enterprise, slots);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> patchCustomEnterpriseService(Long serviceId,
            @Valid PatchCustomEnterpriseServiceRequest request) {
        List<CreateTimeWindowTemplateCommand> createTimeWindowTemplateCommands = request.getTimeWindows()
                .stream()
                .map(TimeWindowMapper::createCommandFromSwaggerTimeWindow)
                .collect(Collectors.toList());

        customAppointmentsEnterpriseServiceService.patchIncludingSlotTemplates(
                serviceId.longValue(),
                Optional.ofNullable(request.getName()),
                Optional.ofNullable(request.getDescription()),
                Optional.ofNullable(request.getLocation()).map(LocationMapper::fromSwagger),
                Optional.ofNullable(request.getTimeZone()).map(ZoneId::of),
                Optional.ofNullable(request.getMaxDistanceKm()),
                Optional.ofNullable(request.getCathegory()).map(EnterpriseServiceCathegory::valueOf),
                Optional.ofNullable(request.getPrice()),
                Optional.ofNullable(request.getCurrency()).map(CurrencyIso::valueOf),
                createTimeWindowTemplateCommands);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> patchNonCustomEnterpriseService(Long serviceId,
            @Valid PatchNonCustomEnterpriseServiceRequest request) {
        List<CreateSlotTemplateCommand> createSlotTemplateCommands = request.getSlots()
                .stream()
                .map(SlotMapper::fromSwaggerSlot)
                .collect(Collectors.toList());

        nonCustomAppointmentsEnterpriseServiceService.patchIncludingSlotTemplates(
                serviceId.longValue(),
                Optional.ofNullable(request.getName()),
                Optional.ofNullable(request.getDescription()),
                Optional.ofNullable(request.getLocation()).map(LocationMapper::fromSwagger),
                Optional.ofNullable(request.getTimeZone()).map(ZoneId::of),
                Optional.ofNullable(request.getCathegory()).map(EnterpriseServiceCathegory::valueOf),
                Optional.ofNullable(request.getPrice()),
                Optional.ofNullable(request.getCurrency()).map(CurrencyIso::valueOf),
                createSlotTemplateCommands);

        return ResponseEntity.ok().build();
    }

}