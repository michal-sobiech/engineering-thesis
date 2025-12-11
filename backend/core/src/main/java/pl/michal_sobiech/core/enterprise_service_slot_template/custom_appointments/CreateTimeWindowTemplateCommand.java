package pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record CreateTimeWindowTemplateCommand(

        DayOfWeek dayOfWeek,

        LocalTime startTime,

        LocalTime endTime

) {

}
