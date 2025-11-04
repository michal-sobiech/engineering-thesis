package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand(

                DayOfWeek dayOfWeek,

                LocalTime startTime,

                LocalTime endTime,

                short maxOccupancy

) {

}
