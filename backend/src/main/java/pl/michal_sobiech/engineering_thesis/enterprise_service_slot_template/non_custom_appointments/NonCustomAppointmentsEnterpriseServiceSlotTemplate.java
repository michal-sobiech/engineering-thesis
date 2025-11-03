package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;

import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;

public record NonCustomAppointmentsEnterpriseServiceSlotTemplate(

        long enterpriseServiceSlotId,

        long enterpriseServiceId,

        DayOfWeek dayOfWeek,

        LocalTime startTime,

        LocalTime endTime,

        Short maxOccupancy

) {

    public static NonCustomAppointmentsEnterpriseServiceSlotTemplate from(EnterpriseServiceSlotTemplateEntity entity) {
        if (entity.getMaxOccupancy() == null) {
            throw new IllegalArgumentException();
        }

        return new NonCustomAppointmentsEnterpriseServiceSlotTemplate(
                entity.getEnterpriseServiceSlotTemplateId(),
                entity.getEnterpriseServiceId(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getMaxOccupancy());
    }

}
