package pl.michal_sobiech.engineering_thesis.enterprise_service_availability_template.non_custom;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments.NonCustomAppointmentsEnterpriseServiceSlotTemplate;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@Service
@RequiredArgsConstructor
public class NonCustomEnterpriseServiceAvailabilityTemplateService {

        private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;

        public List<LocalDateTimeWindow> getAvailabilityTemplateForDatetimeRange(
                        long enterpriseServiceId,
                        LocalDateTime from,
                        LocalDateTime to) {
                List<LocalDateTimeWindow> fullDaysTemplate = getAvailabilityTemplateForDateRange(
                                enterpriseServiceId,
                                from.toLocalDate(),
                                to.toLocalDate());
                System.out.println("BBBBB");
                System.out.println(from);
                System.out.println(to);
                System.out.println(fullDaysTemplate);
                return DateUtils.filterWindowsFullyContainedInRange(fullDaysTemplate, from, to);
        }

        public List<LocalDateTimeWindow> getAvailabilityTemplateForDate(
                        long enterpriseServiceId,
                        LocalDate date) {
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> windowsForDayOfWeek = getAvailabilityTemplateForDayOfWeek(
                                enterpriseServiceId,
                                dayOfWeek);
                return windowsForDayOfWeek.stream()
                                .map(window -> new LocalDateTimeWindow(
                                                LocalDateTime.of(date, window.startTime()),
                                                LocalDateTime.of(date, window.endTime())))
                                .collect(Collectors.toList());
        }

        public List<LocalDateTimeWindow> getAvailabilityTemplateForDateRange(
                        long enterpriseServiceId,
                        LocalDate from,
                        LocalDate to) {
                List<LocalDateTimeWindow> out = new ArrayList<>();

                for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
                        DayOfWeek dayOfWeek = date.getDayOfWeek();
                        System.out.println(dayOfWeek);

                        List<LocalDateTimeWindow> windowsForDate = getAvailabilityTemplateForDate(enterpriseServiceId,
                                        date);

                        out.addAll(windowsForDate);
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
