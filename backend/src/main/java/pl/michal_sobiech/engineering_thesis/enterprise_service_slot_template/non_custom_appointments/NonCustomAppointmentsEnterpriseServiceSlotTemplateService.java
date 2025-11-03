package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@Service
@RequiredArgsConstructor
public class NonCustomAppointmentsEnterpriseServiceSlotTemplateService {

    private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository;
    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;

    public List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> getAllByEnterpriseServiceIdAndDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotTemplateEntity> records = enterpriseServiceSlotTemplateRepository
                .findAllByEnterpriseServiceIdAndDayOfWeek(enterpiseServiceId,
                        dayOfWeek);
        return records.stream().map(record -> NonCustomAppointmentsEnterpriseServiceSlotTemplate.from(record))
                .collect(Collectors.toList());
    }

    public List<LocalDateTimeWindow> getAvailabilityTemplateForDateRange(
            long enterpriseServiceId,
            LocalDate from,
            LocalDate to) {
        List<LocalDateTimeWindow> out = new ArrayList<>();

        for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> windowsForDayOfWeek = getAvailabilityTemplateForDayOfWeek(
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

    public List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> getAvailabilityTemplateForDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotTemplateEntity> slots = enterpriseServiceSlotTemplateService
                .getAvailabilityTemplateForDayOfWeek(enterpiseServiceId, dayOfWeek);
        return slots.stream().map(slot -> NonCustomAppointmentsEnterpriseServiceSlotTemplate.from(slot))
                .collect(Collectors.toList());
    }

}
