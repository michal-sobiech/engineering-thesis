package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand(

                DayOfWeek dayOfWeek,

                LocalTime startTime,

                LocalTime endTime

) {

}
