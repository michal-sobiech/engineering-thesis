package pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service.CreateEnterpriseServiceResult;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotService;

@Service
@RequiredArgsConstructor
public class NoCustomAppointmentsEnterpriseServiceService {

        private final EnterpriseServiceRepository enterpriseServiceRepository;
        private final EnterpriseServiceSlotService enterpriseServiceSlotService;

        @Transactional
        public CreateEnterpriseServiceResult save(long enterpriseId,
                        CreateNoCustomAppointmentsEnterpriseServiceCommand command) {

                EnterpriseServiceEntity service = EnterpriseServiceEntity.builder()
                                .enterpriseId(enterpriseId)
                                .name(command.name())
                                .description(command.description())
                                .timeZone(command.timeZone())
                                .takesCustomAppointments(false)
                                .maxDistanceKm(null)
                                .cathegory(command.cathegory())
                                .price(command.price().orElse(null))
                                .currency(command.currency())
                                .address(command.location().getAddress())
                                .longitude(command.location().getLongitude())
                                .latitude(command.location().getLatitude())
                                .build();

                service = enterpriseServiceRepository.save(service);

                final long enterpriseServiceId = service.getEnterpriseServiceId();
                List<EnterpriseServiceSlotEntity> slots = enterpriseServiceSlotService.saveMany(enterpriseServiceId,
                                command.slots());
                return new CreateEnterpriseServiceResult(service, slots);
        }

}
