package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.ServicesApi;
import org.SwaggerCodeGenExample.model.GetServiceCustomAppointmentsStatus200Response;
import org.SwaggerCodeGenExample.model.GetServiceFreeCustomAppointmentsResponseItem;
import org.SwaggerCodeGenExample.model.GetServiceFreeNonCustomAppointmentsResponseItem;
import org.SwaggerCodeGenExample.model.ServiceSearchResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.custom_appointments.CustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments.NonCustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_search.EnterpriseServiceSearchService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.ServiceSearchSlot;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@RestController
@RequiredArgsConstructor
public class EnterpriseServiceController implements ServicesApi {

    private static final double MAX_VALUE_OF_MAX_DISTANCE_KM = 100;

    private final EnterpriseServiceSearchService enterpriseServiceSearchService;
    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotService;
    private final EnterpriseServiceService enterpriseServiceService;
    private final NonCustomAppointmentsService nonCustomAppointmentsService;
    private final CustomAppointmentsEnterpriseServiceService customAppointmentsEnterpriseServiceService;
    private final NonCustomAppointmentsEnterpriseServiceService nonCustomAppointmentsEnterpriseServiceService;

    @Override
    public ResponseEntity<List<GetServiceFreeNonCustomAppointmentsResponseItem>> getFreeNonCustomAppointments(
            Long enterpriseServiceId,
            LocalDate dateInServiceTimezone) {
        ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(enterpriseServiceId);

        OffsetDateTime start = DateUtils.createOffsetDateTime(dateInServiceTimezone, timezone);
        OffsetDateTime end = start.plusDays(1);

        List<LocalDateTimeWindow> freeSlots = nonCustomAppointmentsEnterpriseServiceService
                .findFreeTimeWindowsInDatetimeRange(enterpriseServiceId, start, end);

        List<GetServiceFreeNonCustomAppointmentsResponseItem> body = freeSlots.stream().map(slot -> {
            return new GetServiceFreeNonCustomAppointmentsResponseItem(
                    DateUtils.createOffsetDateTime(slot.start(), timezone),
                    DateUtils.createOffsetDateTime(slot.end(), timezone));
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

        List<LocalDateTimeWindow> freeWindows = customAppointmentsEnterpriseServiceService
                .findFreeTimeWindowsInDatetimeRange(enterpriseServiceId, start, end);

        List<GetServiceFreeCustomAppointmentsResponseItem> body = freeWindows.stream().map(window -> {
            return new GetServiceFreeCustomAppointmentsResponseItem(
                    DateUtils.createOffsetDateTime(window.start(), timezone),
                    DateUtils.createOffsetDateTime(window.end(), timezone));
        }).collect(Collectors.toList());

        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<GetServiceCustomAppointmentsStatus200Response> getServiceCustomAppointmentsStatus(
            Long serviceId) {
        // TODO
        var body = new GetServiceCustomAppointmentsStatus200Response(false);
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

        List<ServiceSearchSlot> filteredSlots = enterpriseServiceSearchService.searchNoCustomAppointmentsSlots(
                Optional.of(serviceName),
                Optional.of(enterpriseName),
                Optional.of(startDate),
                Optional.of(endDate),
                cathegoryEnum,
                preferredLongitude,
                preferredLatitude,
                maxDistanceKm);
        var body = filteredSlots.stream().map(slot -> new ServiceSearchResponseItem(
                slot.getServiceName(),
                slot.getEnterpriseName(),
                slot.getAddress(),
                slot.getStartTime(),
                slot.getEndTime())).collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }
}
