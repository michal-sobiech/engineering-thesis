package pl.michal_sobiech.core.enterprise_service.no_custom_appointments;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceRepository;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.CreateSlotTemplateCommand;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomAppointmentsEnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.core.location.Location;

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
                .address(command.location().address())
                .longitude(command.location().longitude())
                .latitude(command.location().latitude())
                .build();
        return enterpriseServiceRepository.save(service);
    }

    @Transactional
    public void saveWithSlotTemplates(long enterpriseId,
            CreateNoCustomAppointmentsEnterpriseServiceCommand command,
            List<CreateSlotTemplateCommand> slots) {
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

    @Transactional
    public void patch(
            long enterpriseServiceId,
            Optional<String> name,
            Optional<String> description,
            Optional<Location> location,
            Optional<ZoneId> timezone,
            Optional<EnterpriseServiceCathegory> cathegory,
            Optional<BigDecimal> price,
            Optional<CurrencyIso> currency) {
        EnterpriseServiceEntity record = enterpriseServiceRepository.findById(enterpriseServiceId).orElseThrow();

        name.ifPresent(record::setName);
        description.ifPresent(record::setDescription);
        location.ifPresent(value -> {
            record.setAddress(value.address());
            record.setLongitude(value.longitude());
            record.setLatitude(value.latitude());
        });
        timezone.ifPresent(record::setTimeZone);
        cathegory.ifPresent(record::setCathegory);
        price.ifPresent(record::setPrice);
        currency.ifPresent(record::setCurrency);

        enterpriseServiceRepository.save(record);
    }

    @Transactional
    public void patchIncludingSlotTemplates(
            long enterpriseServiceId,
            Optional<String> name,
            Optional<String> description,
            Optional<Location> location,
            Optional<ZoneId> timezone,
            Optional<EnterpriseServiceCathegory> cathegory,
            Optional<BigDecimal> price,
            Optional<CurrencyIso> currency,
            List<CreateSlotTemplateCommand> slots) {
        patch(enterpriseServiceId, name, description, location, timezone, cathegory, price, currency);
        nonCustomAppointmentsEnterpriseServiceSlotTemplateService
                .overwriteEnterpriseServiceSlotTemplates(enterpriseServiceId, slots);
    }
}
