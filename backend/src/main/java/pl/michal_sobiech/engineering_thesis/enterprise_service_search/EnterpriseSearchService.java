package pl.michal_sobiech.engineering_thesis.enterprise_service_search;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.ServiceSearchSlot;

@Service
@RequiredArgsConstructor
public class EnterpriseSearchService {

    private final EnterpriseServiceSlotRepository enterpriseServiceSlotRepository;

    public List<ServiceSearchSlot> searchNoCustomAppointmentsSlots(
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            Optional<OffsetDateTime> startDate,
            Optional<OffsetDateTime> endDate,
            EnterpriseServiceCathegory cathegory,
            double preferredLongitude,
            double preferredLatitude,
            double maxDistanceChosenByCustomerKm) {
        return enterpriseServiceSlotRepository.filterNoCustomAppointmentsServiceSlots(
                serviceName.orElse(null),
                enterpriseName.orElse(null),
                startDate.orElse(null),
                endDate.orElse(null),
                cathegory,
                preferredLongitude,
                preferredLatitude,
                maxDistanceChosenByCustomerKm);
    }

    public List<ServiceSearchSlot> searchCustomAppointmentsSlots(
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            Optional<OffsetDateTime> startDate,
            Optional<OffsetDateTime> endDate,
            EnterpriseServiceCathegory cathegory,
            double preferredLongitude,
            double preferredLatitude,
            double maxDistanceChosenByCustomerKm) {
        return enterpriseServiceSlotRepository.filterCustomAppointmentsServiceSlots(
                serviceName.orElse(null),
                enterpriseName.orElse(null),
                startDate.orElse(null),
                endDate.orElse(null),
                cathegory,
                preferredLongitude,
                preferredLatitude,
                maxDistanceChosenByCustomerKm);
    }

}
