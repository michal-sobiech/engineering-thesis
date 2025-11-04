package pl.michal_sobiech.engineering_thesis.enterprise_service_search.custom_appointments;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceSearchResultRow;

@Service
@RequiredArgsConstructor
public class CustomAppointmentsEnterpriseServiceSearch {

    private final EnterpriseServiceRepository enterpriseServiceRepository;

    public List<EnterpriseServiceSearchResultRow> search(
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            EnterpriseServiceCathegory cathegory,
            double preferredLongitude,
            double preferredLatitude,
            double maxDistance) {

        boolean takesCustomAppointments = true;

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