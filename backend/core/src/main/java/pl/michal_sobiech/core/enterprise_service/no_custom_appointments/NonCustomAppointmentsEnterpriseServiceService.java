package pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments.CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments.NonCustomAppointmentsEnterpriseServiceSlotTemplateService;


@RequiredArgsConstructor
public class NonCustomAppointmentsEnterpriseServiceService {

        private final EnterpriseServiceRepository enterpriseServiceRepository;
        private final NonCustomAppointmentsEnterpriseServiceSlotTemplateService nonCustomAppointmentsEnterpriseServiceSlotTemplateService;

        @Transactional
        public EnterpriseServiceEntity save(long enterpriseId,
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
                return enterpriseServiceRepository.save(service);
        }

        @Transactional
        public void saveWithSlotTemplates(long enterpriseId,
                        CreateNoCustomAppointmentsEnterpriseServiceCommand command,
                        List<CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand> slots) {
                EnterpriseServiceEntity service = save(enterpriseId, command);

                final long enterpriseServiceId = service.getEnterpriseServiceId();
                nonCustomAppointmentsEnterpriseServiceSlotTemplateService.saveMany(enterpriseServiceId, slots);
        }

        public List<NonCustomEnterpriseService> getByEnterpriseId(long enterpriseId) {
                List<EnterpriseServiceEntity> entities = enterpriseServiceRepository
                                .findNonCustomEnterpriseServicesByEnterpriseId(enterpriseId);
                return entities.stream().map(NonCustomEnterpriseService::fromEntity).collect(Collectors.toList());
        }

        public Optional<NonCustomEnterpriseService> getByEnterpriseServiceId(long enterpriseServiceId) {
                return enterpriseServiceRepository
                                .findById(enterpriseServiceId)
                                .map(NonCustomEnterpriseService::fromEntity);
        }
}
