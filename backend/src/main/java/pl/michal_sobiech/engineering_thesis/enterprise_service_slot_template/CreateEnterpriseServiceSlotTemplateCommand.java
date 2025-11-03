package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record CreateEnterpriseServiceSlotTemplateCommand(

                DayOfWeek dayOfWeek,

                LocalTime startTime,

                LocalTime endTime

) {

}
