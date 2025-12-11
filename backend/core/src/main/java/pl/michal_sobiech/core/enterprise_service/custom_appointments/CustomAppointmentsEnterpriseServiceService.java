package pl.michal_sobiech.core.enterprise_service.custom_appointments;

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
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CreateTimeWindowTemplateCommand;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomTimeWindowTemplateService;
import pl.michal_sobiech.core.location.Location;

@RequiredArgsConstructor
public class CustomAppointmentsEnterpriseServiceService {

    private final EnterpriseServiceRepository enterpriseServiceRepository;
    private final CustomTimeWindowTemplateService timeWindowTemplateService;

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
                .price(command.price())
                .currency(command.currency())
                .address(command.location().address())
                .longitude(command.location().longitude())
                .latitude(command.location().latitude())
                .build();
        service = enterpriseServiceRepository.save(service);
        return CustomEnterpriseService.fromEntity(service);
    }

    @Transactional
    public void saveWithTimeWindows(
            long enterpriseId,
            CreateCustomAppointmentsEnterpriseServiceCommand command,
            List<CreateTimeWindowTemplateCommand> timeWindows) {
        CustomEnterpriseService enterpriseService = save(enterpriseId, command);

        final long enterpriseServiceId = enterpriseService.enterpriseServiceId();
        timeWindowTemplateService.saveMany(
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

    @Transactional
    public void patch(
            long enterpriseServiceId,
            Optional<String> name,
            Optional<String> description,
            Optional<Location> location,
            Optional<ZoneId> timezone,
            Optional<Double> maxDistanceKm,
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
        maxDistanceKm.ifPresent(record::setMaxDistanceKm);
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
            Optional<Double> maxDistanceKm,
            Optional<EnterpriseServiceCathegory> cathegory,
            Optional<BigDecimal> price,
            Optional<CurrencyIso> currency,
            List<CreateTimeWindowTemplateCommand> createTimeWindowCommands) {
        patch(enterpriseServiceId, name, description, location, timezone, maxDistanceKm, cathegory, price, currency);
        timeWindowTemplateService.overwriteEnterpriseServiceTimeWindowTemplates(enterpriseServiceId,
                createTimeWindowCommands);
    }

}
