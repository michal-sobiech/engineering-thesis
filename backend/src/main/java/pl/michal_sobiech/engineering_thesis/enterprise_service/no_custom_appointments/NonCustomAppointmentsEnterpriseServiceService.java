package pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service.CreateEnterpriseServiceResult;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;
import pl.michal_sobiech.engineering_thesis.utils.LocalTimeWindow;

@Service
@RequiredArgsConstructor
public class NonCustomAppointmentsEnterpriseServiceService {

    private final EnterpriseServiceRepository enterpriseServiceRepository;
    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;

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
        List<EnterpriseServiceSlotTemplateEntity> slots = enterpriseServiceSlotTemplateService.saveMany(
                enterpriseServiceId,
                command.slots());
        return new CreateEnterpriseServiceResult(service, slots);
    }

    public List<LocalDateTimeWindowWithOccupancy> getAvailabilityTemplateForDateRange(long enterpriseServiceId,
            LocalDate from,
            LocalDate to) {
        List<LocalDateTimeWindow> out = List.of();
        for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            List<LocalTimeWindow> windowsForDayOfWeek = getAvailabilityTemplateForDayOfWeek(
                    enterpriseServiceId,
                    dayOfWeek);

            List<LocalDateTimeWindow> datetimeWindowsForDayOfWeek = windowsForDayOfWeek.stream()
                    .map(window -> new LocalDateTimeWindowWithOccupancy(
                            LocalDateTime.of(date, window.start()),
                            LocalDateTime.of(date, window.end()))),
                            window.get
                    .toList();

            out.addAll(datetimeWindowsForDayOfWeek);
        }
        return out;
    }

    public List<LocalDateTimeWindowWithOccupancy> getAvailabilityTemplateForDayOfWeek(long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotTemplateEntity> slots = enterpriseServiceSlotTemplateService
                .getAllByEnterpriseServiceIdAndDayOfWeek(enterpiseServiceId, dayOfWeek);
        return slots.stream().map(slot -> {
            return new LocalDateTimeWindowWithOccupancy(
                    slot.getStartTime(),
                    slot.getEndTime(),
                    slot.get);
        }).toList();
    }

}
