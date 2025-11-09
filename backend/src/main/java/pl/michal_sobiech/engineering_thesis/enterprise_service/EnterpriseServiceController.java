package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.ServicesApi;
import org.SwaggerCodeGenExample.model.CreateCustomAppointmentRequest;
import org.SwaggerCodeGenExample.model.CreateEnterpriseServiceReviewRequest;
import org.SwaggerCodeGenExample.model.CreateNonCustomAppointmentRequest;
import org.SwaggerCodeGenExample.model.GetEnterpriseService200Response;
import org.SwaggerCodeGenExample.model.GetServiceCustomAppointmentsStatus200Response;
import org.SwaggerCodeGenExample.model.GetServiceFreeCustomAppointmentsResponseItem;
import org.SwaggerCodeGenExample.model.GetServiceFreeNonCustomAppointmentsResponseItem;
import org.SwaggerCodeGenExample.model.ServiceSearchResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.customer.Customer;
import pl.michal_sobiech.engineering_thesis.enterprise_service.custom_appointments.CustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.custom_appointments.GetEnterpriseServiceCustomServiceResponseFactory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments.GetEnterpriseServiceNonCustomServiceResponseFactory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments.NonCustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_availability.custom.CustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_availability.non_custom.NonCustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.engineering_thesis.non_custom_appointments_enterprise_service_slot_search.NonCustomEnterpriseServiceSlotsSearchService;
import pl.michal_sobiech.engineering_thesis.non_custom_appointments_enterprise_service_slot_search.NonCustomSlotSearchResultRow;
import pl.michal_sobiech.engineering_thesis.review.ReviewService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@RestController
@RequiredArgsConstructor
public class EnterpriseServiceController implements ServicesApi {

        private static final double MAX_VALUE_OF_MAX_DISTANCE_KM = 100;

        private final EnterpriseServiceService enterpriseServiceService;
        private final CustomAppointmentsEnterpriseServiceService customAppointmentsEnterpriseServiceService;
        private final NonCustomAppointmentsEnterpriseServiceService nonCustomAppointmentsEnterpriseServiceService;
        private final NonCustomEnterpriseServiceSlotsSearchService nonCustomEnterpriseServiceSlotsSearchService;
        private final CustomAppointmentsService customAppointmentsService;
        private final NonCustomAppointmentsService nonCustomAppointmentsService;
        private final AuthService authService;
        private final ReviewService reviewService;
        private final NonCustomEnterpriseServiceAvailabilityService nonCustomEnterpriseServiceAvailabilityService;
        private final CustomEnterpriseServiceAvailabilityService customEnterpriseServiceAvailabilityService;

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
                        Long enterpriseServiceId, LocalDate dateInServiceTimezone) {
                ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(enterpriseServiceId);

                OffsetDateTime start = DateUtils.createOffsetDateTime(dateInServiceTimezone, timezone);
                OffsetDateTime end = start.plusDays(1);

                List<LocalDateTimeWindow> freeWindows = customEnterpriseServiceAvailabilityService
                                .findFreeTimeWindowsInDatetimeRange(enterpriseServiceId, start, end);

                List<GetServiceFreeCustomAppointmentsResponseItem> body = freeWindows.stream().map(window -> {
                        return new GetServiceFreeCustomAppointmentsResponseItem(
                                        DateUtils.extractHHmmTimeFromLocalDateTime(window.start()),
                                        DateUtils.extractHHmmTimeFromLocalDateTime(window.end()));
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
                        System.out.println("A123");
                        return HttpUtils.createBadRequestResponse();
                }

                Optional<EnterpriseServiceCathegory> optionalCathegoryEnum = Optional.ofNullable(
                                EnterpriseServiceCathegory.enterpriseServiceCathegoryToString.inverse().get(cathegory));
                if (optionalCathegoryEnum.isEmpty()) {
                        System.out.println("B123");
                        return HttpUtils.createBadRequestResponse();
                }
                EnterpriseServiceCathegory cathegoryEnum = optionalCathegoryEnum.get();

                List<NonCustomSlotSearchResultRow> availableNonCustomSlots = nonCustomEnterpriseServiceSlotsSearchService
                                .searchNoCustomAppointmentsSlots(
                                                Optional.ofNullable(serviceName),
                                                Optional.ofNullable(enterpriseName),
                                                Optional.ofNullable(startDate),
                                                Optional.of(endDate),
                                                cathegoryEnum,
                                                preferredLongitude,
                                                preferredLatitude,
                                                maxDistanceKm);

                List<ServiceSearchResponseItem> body = availableNonCustomSlots.stream()
                                .map(slot -> new ServiceSearchResponseItem(
                                                slot.enterpriseServiceName(),
                                                slot.enterpriseName(),
                                                slot.address(),
                                                OffsetDateTime.ofInstant(slot.start(), ZoneOffset.UTC),
                                                OffsetDateTime.ofInstant(slot.end(), ZoneOffset.UTC)))
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

                customAppointmentsService.createCustomAppointment(
                                enterpriseServiceId,
                                customer.getUserId(),
                                startInstant,
                                endInstant,
                                request.getLocation());
                return ResponseEntity.ok().build();
        }

        @Override
        public ResponseEntity<Void> createNonCustomAppointment(
                        Long enterpriseServiceId,
                        CreateNonCustomAppointmentRequest request) {
                Customer customer = authService.requireCustomer();

                // TODO check if appointment can be made

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
                short numStarsOutOf5 = request.getNumStarsOutOf5().shortValue();
                if (numStarsOutOf5 < 1 || numStarsOutOf5 > 5) {
                        throw new IllegalArgumentException();
                }

                Customer customer = authService.requireCustomer();
                reviewService.createReview(
                                customer.getUserId(),
                                serviceId,
                                request.getNumStarsOutOf5().shortValue(),
                                request.getText());
                return ResponseEntity.ok().build();
        }

}
