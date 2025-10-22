package pl.michal_sobiech.engineering_thesis.enterprise_service_slot;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record CreateEnterpriseServiceSlotCommand(

                DayOfWeek dayOfWeek,

                LocalTime startTime,

                LocalTime endTime

) {

}
