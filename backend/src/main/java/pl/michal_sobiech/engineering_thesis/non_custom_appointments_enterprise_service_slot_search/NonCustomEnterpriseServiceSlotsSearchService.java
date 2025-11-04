package pl.michal_sobiech.engineering_thesis.non_custom_appointments_enterprise_service_slot_search;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceSearchResultRow;
import pl.michal_sobiech.engineering_thesis.enterprise_service_availability.non_custom.NonCustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_search.non_custom_appointments.NonCustomAppointmentsEnterpriseServiceSearchService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@Service
@RequiredArgsConstructor
public class NonCustomEnterpriseServiceSlotsSearchService {

    private static final int DEFAULT_SEARCH_RANGE_DAYS = 7;

    private final NonCustomAppointmentsEnterpriseServiceSearchService nonCustomAppointmentsEnterpriseServiceSearchService;
    private final NonCustomEnterpriseServiceAvailabilityService nonCustomEnterpriseServiceAvailabilityService;

    public List<NonCustomSlotSearchResultRow> searchNoCustomAppointmentsSlots(
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            Optional<OffsetDateTime> startDate,
            Optional<OffsetDateTime> endDate,
            EnterpriseServiceCathegory cathegory,
            double preferredLongitude,
            double preferredLatitude,
            double maxDistanceChosenByCustomerKm) {
        // 1. Find services with matching name, enteprise name, cathegory and location
        // 2. Load availability for them

        OffsetDateTime finalStartDatetime = startDate.orElse(OffsetDateTime.now());
        OffsetDateTime finalEndDatetime = endDate
                .orElse(finalStartDatetime.plusDays(DEFAULT_SEARCH_RANGE_DAYS));

        // 1.
        List<EnterpriseServiceSearchResultRow> services = nonCustomAppointmentsEnterpriseServiceSearchService
                .search(
                        serviceName,
                        enterpriseName, cathegory, preferredLongitude, preferredLatitude,
                        maxDistanceChosenByCustomerKm);

        // 2.
        List<NonCustomSlotSearchResultRow> availableSlots = services.stream()
                .map(service -> {
                    LocalDateTime start = DateUtils.createLocalDateTime(finalStartDatetime,
                            service.getTimezone());
                    LocalDateTime end = DateUtils.createLocalDateTime(finalEndDatetime,
                            service.getTimezone());
                    List<LocalDateTimeWindow> windows = nonCustomEnterpriseServiceAvailabilityService
                            .getAvailableSlotsInDatetimeRange(
                                    service.getEnterpriseServiceId(),
                                    start,
                                    end);
                    List<NonCustomSlotSearchResultRow> windowsGlobal = windows.stream()
                            .map(window -> new NonCustomSlotSearchResultRow(
                                    service.getEnterpriseServiceId(),
                                    service.getServiceName(),
                                    service.getEnterpriseName(),
                                    service.getAddress(),
                                    service.getPrice(),
                                    service.getTimezone(),
                                    DateUtils.createInstant(window.start(),
                                            service.getTimezone()),
                                    DateUtils.createInstant(window.end(),
                                            service.getTimezone())))
                            .collect(Collectors.toList());
                    return windowsGlobal;
                }).flatMap(List::stream).collect(Collectors.toList());

        return availableSlots;
    }

}
