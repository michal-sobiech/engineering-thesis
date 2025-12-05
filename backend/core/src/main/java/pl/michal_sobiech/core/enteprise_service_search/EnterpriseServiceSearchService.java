package pl.michal_sobiech.core.enteprise_service_search;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceRepository;

@RequiredArgsConstructor
public class EnterpriseServiceSearchService {

    private final EnterpriseServiceRepository enterpriseServiceRepository;

    public List<EnterpriseServiceSearchResultRow> search(
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            EnterpriseServiceCathegory cathegory,
            double preferredLongitude,
            double preferredLatitude,
            double maxDistance,
            boolean takesCustomAppointments) {
        return enterpriseServiceRepository.search(
                takesCustomAppointments,
                serviceName.orElse(null),
                enterpriseName.orElse(null),
                cathegory,
                preferredLatitude,
                preferredLongitude,
                maxDistance);
    }

}
