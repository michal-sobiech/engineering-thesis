package pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record CreateSlotTemplateCommand(

        DayOfWeek dayOfWeek,

        LocalTime startTime,

        LocalTime endTime,

        short maxOccupancy

) {

}
