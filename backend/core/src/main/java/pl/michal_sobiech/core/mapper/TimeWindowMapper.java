package pl.michal_sobiech.core.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomSlotTemplate;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplate;
import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindowWithCapacity;
import pl.michal_sobiech.core.utils.local_datetime_window.SimpleLocalDatetimeWindow;

public class TimeWindowMapper {

    public static LocalDatetimeWindowWithCapacity toLocalDatetimeWindowWithCapacity(
            NonCustomSlotTemplate slotTemplate, LocalDate date) {
        LocalDateTime start = date.atTime(slotTemplate.start());
        LocalDateTime end = date.atTime(slotTemplate.end());
        return new LocalDatetimeWindowWithCapacity(start, end, slotTemplate.maxOccupancy());
    }

    public static SimpleLocalDatetimeWindow toSimpleLocalDatetimeWindow(
            CustomSlotTemplate slotTemplate, LocalDate date) {
        LocalDateTime start = date.atTime(slotTemplate.start());
        LocalDateTime end = date.atTime(slotTemplate.end());
        return new SimpleLocalDatetimeWindow(start, end);
    }

}
