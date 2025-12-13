package pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.core.utils.DateUtils;
import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindow;

@RequiredArgsConstructor
public class CustomTimeWindowTemplateService {

    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;
    private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository;

    @Transactional
    public List<CustomSlotTemplate> saveMany(long enterpriseServiceId,
            List<CreateTimeWindowTemplateCommand> commands) {
        return commands.stream().map(command -> save(enterpriseServiceId, command))
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomSlotTemplate save(long enterpriseServiceId,
            CreateTimeWindowTemplateCommand command) {
        EnterpriseServiceSlotTemplateEntity slot = EnterpriseServiceSlotTemplateEntity.builder()
                .enterpriseServiceId(enterpriseServiceId)
                .dayOfWeek(command.dayOfWeek())
                .startTime(command.startTime())
                .endTime(command.endTime())
                .build();
        slot = enterpriseServiceSlotTemplateRepository.save(slot);
        return CustomSlotTemplate.from(slot);
    }

    public List<LocalDatetimeWindow> getAvailabilityTemplateForLocalDatetimeRange(
            long enterpriseServiceId,
            LocalDateTime from,
            LocalDateTime to) {
        List<LocalDatetimeWindow> fullDaysTemplate = getAvailabilityTemplateForDateRange(
                enterpriseServiceId,
                from.toLocalDate(),
                to.toLocalDate());
        return DateUtils.filterWindowsFullyContainedInRange(fullDaysTemplate, from, to);
    }

    public List<LocalDatetimeWindow> getAvailabilityTemplateForDateRange(
            long enterpriseServiceId,
            LocalDate from,
            LocalDate to) {
        List<LocalDatetimeWindow> out = new ArrayList<>();

        for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            List<CustomSlotTemplate> windowsForDayOfWeek = getAvailabilityTemplateForDayOfWeek(
                    enterpriseServiceId,
                    dayOfWeek);
            List<LocalDatetimeWindow> mappedWindowsForDayOfWeek = windowsForDayOfWeek.stream()
                    .map(window -> new LocalDatetimeWindow(
                            LocalDateTime.of(date, window.startTime()),
                            LocalDateTime.of(date, window.endTime())))
                    .collect(Collectors.toList());

            out.addAll(mappedWindowsForDayOfWeek);
        }
        return out;
    }

    public List<LocalDatetimeWindow> getAvailabilityTemplateOnDate(
            long enterpriseServiceId,
            LocalDate date) {
        return getAvailabilityTemplateForDateRange(enterpriseServiceId, date, date);
    }

    public List<CustomSlotTemplate> getAvailabilityTemplateForDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotTemplateEntity> slots = enterpriseServiceSlotTemplateService
                .getAvailabilityTemplateForDayOfWeek(enterpiseServiceId, dayOfWeek);
        return slots.stream()
                .map(CustomSlotTemplate::from)
                .collect(Collectors.toList());
    }

    public List<CustomSlotTemplate> getAllInWeekByEnterpriseServiceId(
            long enterpiseServiceId) {
        return Arrays.stream(DayOfWeek.values())
                .map(day -> getAvailabilityTemplateForDayOfWeek(enterpiseServiceId, day))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Transactional
    public void overwriteEnterpriseServiceTimeWindowTemplates(
            long enterpriseServiceId,
            List<CreateTimeWindowTemplateCommand> commands) {
        deleteEnterpriseServiceTimeWindowTemplates(enterpriseServiceId);
        saveMany(enterpriseServiceId, commands);
    }

    @Transactional
    public void deleteEnterpriseServiceTimeWindowTemplates(long enterpiseServiceId) {
        enterpriseServiceSlotTemplateRepository.deleteAllByEnterpriseServiceId(enterpiseServiceId);
    }

}
