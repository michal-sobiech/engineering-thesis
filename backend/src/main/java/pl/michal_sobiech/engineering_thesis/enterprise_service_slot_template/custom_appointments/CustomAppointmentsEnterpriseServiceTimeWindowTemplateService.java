package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@Service
@RequiredArgsConstructor
public class CustomAppointmentsEnterpriseServiceTimeWindowTemplateService {

    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;
    private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository;

    @Transactional
    public List<CustomAppointmentsEnterpriseServiceSlotTemplate> saveMany(long enterpriseServiceId,
            List<CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand> commands) {
        return commands.stream().map(command -> save(enterpriseServiceId, command)).collect(Collectors.toList());
    }

    @Transactional
    public CustomAppointmentsEnterpriseServiceSlotTemplate save(long enterpriseServiceId,
            CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand command) {
        EnterpriseServiceSlotTemplateEntity slot = EnterpriseServiceSlotTemplateEntity.builder()
                .enterpriseServiceId(enterpriseServiceId)
                .dayOfWeek(command.dayOfWeek())
                .startTime(command.startTime())
                .endTime(command.endTime())
                .build();
        slot = enterpriseServiceSlotTemplateRepository.save(slot);
        return CustomAppointmentsEnterpriseServiceSlotTemplate.from(slot);
    }

    public List<LocalDateTimeWindow> getAvailabilityTemplateForDatetimeRange(
            long enterpriseServiceId,
            LocalDateTime from,
            LocalDateTime to) {
        List<LocalDateTimeWindow> fullDaysTemplate = getAvailabilityTemplateForDateRange(
                enterpriseServiceId,
                from.toLocalDate(),
                to.toLocalDate());
        return DateUtils.filterWindowsFullyContainedInRange(fullDaysTemplate, from, to);
    }

    public List<LocalDateTimeWindow> getAvailabilityTemplateForDateRange(long enterpriseServiceId,
            LocalDate from,
            LocalDate to) {
        List<LocalDateTimeWindow> out = new ArrayList<>();

        for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            List<CustomAppointmentsEnterpriseServiceSlotTemplate> windowsForDayOfWeek = getAvailabilityTemplateForDayOfWeek(
                    enterpriseServiceId,
                    dayOfWeek);
            List<LocalDateTimeWindow> mappedWindowsForDayOfWeek = windowsForDayOfWeek.stream()
                    .map(window -> new LocalDateTimeWindow(
                            LocalDateTime.of(date, window.startTime()),
                            LocalDateTime.of(date, window.endTime())))
                    .collect(Collectors.toList());

            out.addAll(mappedWindowsForDayOfWeek);
        }
        return out;
    }

    public List<LocalDateTimeWindow> getAvailabilityTemplateOnDate(
            long enterpriseServiceId,
            LocalDate date) {
        return getAvailabilityTemplateForDateRange(enterpriseServiceId, date, date);
    }

    public List<CustomAppointmentsEnterpriseServiceSlotTemplate> getAvailabilityTemplateForDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotTemplateEntity> slots = enterpriseServiceSlotTemplateService
                .getAvailabilityTemplateForDayOfWeek(enterpiseServiceId, dayOfWeek);
        return slots.stream().map(slot -> CustomAppointmentsEnterpriseServiceSlotTemplate.from(slot))
                .collect(Collectors.toList());
    }

}
