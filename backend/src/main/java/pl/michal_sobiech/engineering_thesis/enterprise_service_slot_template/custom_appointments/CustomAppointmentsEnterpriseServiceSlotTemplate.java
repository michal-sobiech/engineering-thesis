package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;

import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;

public record CustomAppointmentsEnterpriseServiceSlotTemplate(

        long enterpriseServiceSlotId,

        long enterpriseServiceId,

        DayOfWeek dayOfWeek,

        LocalTime startTime,

        LocalTime endTime

) {

    public static CustomAppointmentsEnterpriseServiceSlotTemplate from(EnterpriseServiceSlotTemplateEntity entity) {
        if (entity.getMaxOccupancy() != null) {
            throw new IllegalArgumentException();
        }

        return new CustomAppointmentsEnterpriseServiceSlotTemplate(
                entity.getEnterpriseServiceSlotId(),
                entity.getEnterpriseServiceId(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime());
    }

}
