package pl.michal_sobiech.core.enterprise_service.custom_appointments;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceRepository;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomAppointmentsEnterpriseServiceTimeWindowTemplateService;

@RequiredArgsConstructor
public class CustomAppointmentsEnterpriseServiceService {

    private final EnterpriseServiceRepository enterpriseServiceRepository;
    private final CustomAppointmentsEnterpriseServiceTimeWindowTemplateService customAppointmentsEnterpriseServiceTimeWindowTemplateService;

    @Transactional
    public CustomEnterpriseService save(long enterpriseId,
            CreateCustomAppointmentsEnterpriseServiceCommand command) {
        EnterpriseServiceEntity service = EnterpriseServiceEntity.builder()
                .enterpriseId(enterpriseId)
                .name(command.name())
                .description(command.description())
                .timeZone(command.timeZone())
                .takesCustomAppointments(true)
                .maxDistanceKm(command.maxDistanceKm())
                .cathegory(command.cathegory())
                .price(command.price().orElse(null))
                .currency(command.currency())
                .address(command.location().address())
                .longitude(command.location().position().longitude())
                .latitude(command.location().position().latitude())
                .build();
        service = enterpriseServiceRepository.save(service);
        return CustomEnterpriseService.fromEntity(service);
    }

    @Transactional
    public void saveWithTimeWindows(
            long enterpriseId,
            CreateCustomAppointmentsEnterpriseServiceCommand command,
            List<CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand> timeWindows) {
        CustomEnterpriseService enterpriseService = save(enterpriseId, command);

        final long enterpriseServiceId = enterpriseService.enterpriseServiceId();
        customAppointmentsEnterpriseServiceTimeWindowTemplateService.saveMany(
                enterpriseServiceId,
                timeWindows);
    }

    public List<CustomEnterpriseService> getByEnterpriseId(long enterpriseId) {
        List<EnterpriseServiceEntity> entities = enterpriseServiceRepository
                .findCustomEnterpriseServicesByEnterpriseId(enterpriseId);
        return entities.stream().map(CustomEnterpriseService::fromEntity).collect(Collectors.toList());
    }

    public Optional<CustomEnterpriseService> getByEnterpriseServiceId(long enterpriseServiceId) {
        return enterpriseServiceRepository.findById(enterpriseServiceId)
                .map(CustomEnterpriseService::fromEntity);
    }

}
