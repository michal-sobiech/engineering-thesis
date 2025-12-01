package pl.michal_sobiech.engineering_thesis.available_enterprise_service_time_windows_search.custom;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enteprise_service_search.EnterpriseServiceSearchResultRow;
import pl.michal_sobiech.engineering_thesis.enteprise_service_search.EnterpriseServiceSearchService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.engineering_thesis.enterprise_service_availability.CustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@Service
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

        // TODO where is subtracting?????
    }

    private List<AvailableCustomEnterpriseServiceFreeTimeWindowSearchRow> getGlobalTimeWindowsForService(
            EnterpriseServiceSearchResultRow service,
            Instant start,
            Instant end) {
        LocalDateTime startLocal = DateUtils.createLocalDateTime(start, service.getTimezone());
        LocalDateTime endLocal = DateUtils.createLocalDateTime(end, service.getTimezone());

        List<LocalDateTimeWindow> windows = customEnterpriseServiceAvailabilityService
                .findFreeTimeWindowsInLocalDatetimeRangeForService(
                        service.getEnterpriseServiceId(),
                        startLocal,
                        endLocal);
        List<AvailableCustomEnterpriseServiceFreeTimeWindowSearchRow> windowsGlobal = windows.stream()
                .map(window -> new AvailableCustomEnterpriseServiceFreeTimeWindowSearchRow(
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
    }
}
