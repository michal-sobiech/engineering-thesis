package pl.michal_sobiech.core.available_enterprise_service_time_windows_search.custom;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enteprise_service_search.EnterpriseServiceSearchResultRow;
import pl.michal_sobiech.core.enteprise_service_search.EnterpriseServiceSearchService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.core.enterprise_service_availability.CustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.core.utils.instant_window.InstantWindow;

@RequiredArgsConstructor
public class AvailableCustomEnterpriseServiceFreeTimeWindowSearchService {

    private final CustomEnterpriseServiceAvailabilityService customEnterpriseServiceAvailabilityService;
    private final EnterpriseServiceSearchService enterpriseServiceSearchService;

    public List<AvailableCustomEnterpriseServiceFreeTimeWindowSearchRow> searchFreeTimeWindows(
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            Instant start,
            Instant end,
            EnterpriseServiceCathegory cathegory,
            double preferredLongitude,
            double preferredLatitude,
            double maxDistanceChosenByCustomerKm) {
        // 1. Find services with matching name, enterprise name, cathegory and location
        // 2. Load availability for them

        // 1.
        List<EnterpriseServiceSearchResultRow> services = enterpriseServiceSearchService.search(
                serviceName,
                enterpriseName, cathegory, preferredLongitude, preferredLatitude,
                maxDistanceChosenByCustomerKm, true);

        // 2.
        return services.stream()
                .map(service -> getGlobalTimeWindowsForService(service, start, end))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<AvailableCustomEnterpriseServiceFreeTimeWindowSearchRow> getGlobalTimeWindowsForService(
            EnterpriseServiceSearchResultRow service,
            Instant start,
            Instant end) {
        List<InstantWindow> windows = customEnterpriseServiceAvailabilityService
                .calcServiceAvailability(service.getEnterpriseServiceId(), start, end);

        List<AvailableCustomEnterpriseServiceFreeTimeWindowSearchRow> windowsGlobal = windows.stream()
                .map(window -> new AvailableCustomEnterpriseServiceFreeTimeWindowSearchRow(
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
