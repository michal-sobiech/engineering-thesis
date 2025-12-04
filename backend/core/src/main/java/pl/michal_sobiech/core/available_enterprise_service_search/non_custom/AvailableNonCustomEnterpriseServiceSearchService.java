package pl.michal_sobiech.engineering_thesis.available_enterprise_service_search.non_custom;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.available_enterprise_service_search.AvailableEnterpriseServiceSearchResultRow;
import pl.michal_sobiech.engineering_thesis.available_enterprise_service_time_windows_search.non_custom.AvailableEnterpriseServiceTimeWindowsSearchResultRow;
import pl.michal_sobiech.engineering_thesis.available_enterprise_service_time_windows_search.non_custom.AvailableNonCustomEnterpriseServiceTimeWindowsSearchService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;


@RequiredArgsConstructor
public class AvailableNonCustomEnterpriseServiceSearchService {

    private final AvailableNonCustomEnterpriseServiceTimeWindowsSearchService timeWindowsSearchService;

    public List<AvailableEnterpriseServiceSearchResultRow> searchAvailableServices(
            double preferredLongitude,
            double preferredLatitude,
            double maxDistance,
            EnterpriseServiceCathegory cathegory,
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            Instant startInstant,
            Instant endInstant) {
        // Available service = at least one available time window in given time range
        List<AvailableEnterpriseServiceTimeWindowsSearchResultRow> availableTimeWindows = timeWindowsSearchService
                .searchAvailableTimeWindows(
                        serviceName,
                        enterpriseName,
                        startInstant,
                        endInstant,
                        cathegory,
                        preferredLongitude,
                        preferredLatitude,
                        maxDistance);
        Map<Long, AvailableEnterpriseServiceTimeWindowsSearchResultRow> timeWindowsNoDuplicates = new HashMap<>();
        for (var window : availableTimeWindows) {
            timeWindowsNoDuplicates.putIfAbsent(window.enterpriseServiceId(), window);
        }

        return timeWindowsNoDuplicates
                .values()
                .stream()
                .map(window -> new AvailableEnterpriseServiceSearchResultRow(
                        window.enterpriseServiceId(),
                        window.enterpriseServiceName(),
                        window.enterpriseName(),
                        window.address(),
                        window.price()))
                .collect(Collectors.toList());
    }

}