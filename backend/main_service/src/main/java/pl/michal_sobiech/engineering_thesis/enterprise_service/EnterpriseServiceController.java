package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
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
import pl.michal_sobiech.core.customer.Customer;
import pl.michal_sobiech.core.enterprise.Enterprise;
import pl.michal_sobiech.core.enterprise.EnterpriseService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomEnterpriseService;
import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.NonCustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service_availability.CustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.core.enterprise_service_availability.NonCustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.core.location.Location;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatusNotPaid;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatusPaidOnSite;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatusPaidOnline;
import pl.michal_sobiech.core.review.ReviewService;
import pl.michal_sobiech.core.utils.DateUtils;
import pl.michal_sobiech.core.utils.LocalDateTimeWindow;
import pl.michal_sobiech.engineering_thesis.api.LocationMapper;
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

    @Override
    public ResponseEntity<List<GetServiceFreeNonCustomAppointmentsResponseItem>> getFreeNonCustomAppointments(
            Long enterpriseServiceId,
            LocalDate dateInServiceTimezone) {
        ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(enterpriseServiceId);

        OffsetDateTime start = DateUtils.createOffsetDateTime(dateInServiceTimezone, timezone);
        OffsetDateTime end = start.plusDays(1);

        List<LocalDateTimeWindow> freeSlots = nonCustomEnterpriseServiceAvailabilityService
                .findFreeTimeWindowsInDatetimeRange(enterpriseServiceId, start, end);

        List<GetServiceFreeNonCustomAppointmentsResponseItem> body = freeSlots.stream().map(slot -> {
            return new GetServiceFreeNonCustomAppointmentsResponseItem(
                    DateUtils.extractHHmmTimeFromLocalDateTime(slot.start()),
                    DateUtils.extractHHmmTimeFromLocalDateTime(slot.end()));
        }).collect(Collectors.toList());

        System.out.println(body);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetServiceFreeCustomAppointmentsResponseItem>> getFreeTimeWindowsForCustomAppointments(
            Long enterpriseServiceId,
            LocalDate dateInServiceTimezone) {
        LocalDateTime startLocal = dateInServiceTimezone.atStartOfDay();
        LocalDateTime endLocal = startLocal.plusDays(1);

        List<LocalDateTimeWindow> freeWindows = customEnterpriseServiceAvailabilityService
                .findFreeTimeWindowsInLocalDatetimeRangeForService(enterpriseServiceId, startLocal,
                        endLocal);

        List<GetServiceFreeCustomAppointmentsResponseItem> body = freeWindows
                .stream()
                .map(window -> new GetServiceFreeCustomAppointmentsResponseItem(
                        DateUtils.extractHHmmTimeFromLocalDateTime(window.start()),
                        DateUtils.extractHHmmTimeFromLocalDateTime(window.end())))
                .collect(Collectors.toList());

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

        LocalDateTime startParsed = LocalDateTime.parse(request.getStartDatetimeShopLocal());
        Instant startInstant = startParsed.atZone(timezone).toInstant();

        LocalDateTime endParsed = LocalDateTime.parse(request.getEndDatetimeShopLocal());
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

        LocalDateTime startParsed = LocalDateTime.parse(request.getStartDatetimeShopLocal());
        Instant startInstant = startParsed.atZone(timezone).toInstant();

        LocalDateTime endParsed = LocalDateTime.parse(request.getEndDatetimeShopLocal());
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
            var enterprise = customAppointmentsEnterpriseServiceService
                    .getByEnterpriseServiceId(enterpriseServiceId)
                    .orElseThrow();
            body = GetEnterpriseServiceCustomServiceResponseFactory.fromDomain(enterprise);
        } else {
            var enterprise = nonCustomAppointmentsEnterpriseServiceService
                    .getByEnterpriseServiceId(enterpriseServiceId)
                    .orElseThrow();
            body = GetEnterpriseServiceNonCustomServiceResponseFactory.fromDomain(enterprise);
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

        org.SwaggerCodeGenExample.model.Location swaggerLocation = LocationMapper.fromDomain(service.location());

        var body = new GetEnterpriseServiceCustomServiceResponse(
                true,
                enterprise.enterpriseId(),
                enterprise.name(),
                serviceId,
                service.name(),
                service.description(),
                swaggerLocation,
                service.timezone().toString(),
                service.maxDistanceKm(),
                service.cathegory().toString(),
                service.price(),
                service.currency().toString());

        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<GetEnterpriseServiceNonCustomServiceResponse> getNonCustomEnterpriseService(Long serviceId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNonCustomEnterpriseService'");
    }

    @Override
    public ResponseEntity<Void> patchCustomEnterpriseService(Long serviceId,
            @Valid PatchCustomEnterpriseServiceRequest patchCustomEnterpriseServiceRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patchCustomEnterpriseService'");
    }

    @Override
    public ResponseEntity<Void> patchNonCustomEnterpriseService(Long serviceId,
            @Valid PatchNonCustomEnterpriseServiceRequest patchNonCustomEnterpriseServiceRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patchNonCustomEnterpriseService'");
    }

}