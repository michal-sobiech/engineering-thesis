package pl.michal_sobiech.core.available_enterprise_service_search;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.available_enterprise_service_search.custom.AvailableCustomEnterpriseServiceSearchService;
import pl.michal_sobiech.core.available_enterprise_service_search.non_custom.AvailableNonCustomEnterpriseServiceSearchService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;

@RequiredArgsConstructor
public class AvailableEnterpriseServiceSearchService {

    private final AvailableNonCustomEnterpriseServiceSearchService availableNonCustomEnterpriseServiceSearchService;
    private final AvailableCustomEnterpriseServiceSearchService availableCustomEnterpriseServiceSearchService;

    public List<AvailableEnterpriseServiceSearchResultRow> searchAvailableServices(
            double preferredLongitude,
            double preferredLatitude,
            double maxDistance,
            EnterpriseServiceCathegory cathegory,
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            Instant startInstant,
            Instant endInstant) {

        List<AvailableEnterpriseServiceSearchResultRow> nonCustomServices = availableNonCustomEnterpriseServiceSearchService
                .searchAvailableServices(
                        preferredLongitude,
                        preferredLatitude,
                        maxDistance,
                        cathegory,
                        serviceName,
                        enterpriseName,
                        startInstant,
                        endInstant);

        List<AvailableEnterpriseServiceSearchResultRow> customServices = availableCustomEnterpriseServiceSearchService
                .searchAvailableServices(
                        preferredLongitude,
                        preferredLatitude,
                        maxDistance,
                        cathegory,
                        serviceName,
                        enterpriseName,
                        startInstant,
                        endInstant);

        List<AvailableEnterpriseServiceSearchResultRow> out = new ArrayList<>();
        out.addAll(nonCustomServices);
        out.addAll(customServices);
        return out;
    }

}
