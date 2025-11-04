package pl.michal_sobiech.engineering_thesis.enterprise_service_search;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;

@Service
@RequiredArgsConstructor
public class EnterpriseServiceSearchService {

    private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotRepository;

    // public List<

    // public List<ServiceSearchSlot> searchNoCustomAppointmentsSlots(
    // Optional<String> serviceName,
    // Optional<String> enterpriseName,
    // Optional<OffsetDateTime> startDate,
    // Optional<OffsetDateTime> endDate,
    // EnterpriseServiceCathegory cathegory,
    // double preferredLongitude,
    // double preferredLatitude,
    // double maxDistanceChosenByCustomerKm) {

    // }

    // private

    // public List<ServiceSearchSlot> searchCustomAppointmentsSlots(
    // Optional<String> serviceName,
    // Optional<String> enterpriseName,
    // Optional<OffsetDateTime> startDate,
    // Optional<OffsetDateTime> endDate,
    // EnterpriseServiceCathegory cathegory,
    // double preferredLongitude,
    // double preferredLatitude,
    // double maxDistanceChosenByCustomerKm) {
    // // String cathegoryString =
    // //
    // EnterpriseServiceCathegory.enterpriseServiceCathegoryToString.get(cathegory);
    // return enterpriseServiceSlotRepository.filterCustomAppointmentsServiceSlots(
    // serviceName.orElse(null),
    // enterpriseName.orElse(null),
    // startDate.orElse(null),
    // endDate.orElse(null),
    // cathegory,
    // // cathegoryString,
    // preferredLongitude,
    // preferredLatitude,
    // maxDistanceChosenByCustomerKm);
    // }

}
