package pl.michal_sobiech.core.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplate;
import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindowWithCapacity;

public class TimeWindowMapper {

    public static LocalDatetimeWindowWithCapacity toLocalDatetimeWindowWithCapacity(
            NonCustomSlotTemplate slot, LocalDate date) {
        LocalDateTime start = date.atTime(slot.start());
        LocalDateTime end = date.atTime(slot.end());
        return new LocalDatetimeWindowWithCapacity(start, end, slot.maxOccupancy());
    }

}
