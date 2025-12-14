package pl.michal_sobiech.core.available_enterprise_service_time_windows_search.non_custom;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enteprise_service_search.EnterpriseServiceSearchResultRow;
import pl.michal_sobiech.core.enteprise_service_search.EnterpriseServiceSearchService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.core.enterprise_service_availability.NonCustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.core.utils.instant_window.InstantWindow;

@RequiredArgsConstructor
public class AvailableNonCustomEnterpriseServiceTimeWindowsSearchService {

    private final EnterpriseServiceSearchService enterpriseServiceSearchService;
    private final NonCustomEnterpriseServiceAvailabilityService nonCustomEnterpriseServiceAvailabilityService;

    public List<AvailableEnterpriseServiceTimeWindowsSearchResultRow> searchAvailableTimeWindows(
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            Instant startInstant,
            Instant endInstant,
            EnterpriseServiceCathegory cathegory,
            double preferredLongitude,
            double preferredLatitude,
            double maxDistanceChosenByCustomerKm) {
        // 1. Find services with matching name, enterprise name, cathegory and location
        // 2. Load availability for them

        // 1.
        List<EnterpriseServiceSearchResultRow> services = enterpriseServiceSearchService.search(
                serviceName,
                enterpriseName,
                cathegory,
                preferredLongitude,
                preferredLatitude,
                maxDistanceChosenByCustomerKm,
                false);

        // 2.
        List<AvailableEnterpriseServiceTimeWindowsSearchResultRow> availableSlots = services
                .stream()
                .map(service -> getGlobalSlotsForService(service, startInstant, endInstant))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // TODO where is subtracting?

        return availableSlots;
    }

    private List<AvailableEnterpriseServiceTimeWindowsSearchResultRow> getGlobalSlotsForService(
            EnterpriseServiceSearchResultRow service, Instant start, Instant end) {

        List<InstantWindow> windows = nonCustomEnterpriseServiceAvailabilityService
                .calcServiceAvailability(service.getEnterpriseServiceId(), start, end);

        List<AvailableEnterpriseServiceTimeWindowsSearchResultRow> windowsGlobal = windows.stream()
                .map(window -> new AvailableEnterpriseServiceTimeWindowsSearchResultRow(
                        service.getEnterpriseServiceId(),
                        service.getServiceName(),
                        service.getEnterpriseName(),
                        service.getAddress(),
                        service.getPrice(),
                        service.getTimezone(),
                        start,
                        end))
                .collect(Collectors.toList());
        return windowsGlobal;
    }
}