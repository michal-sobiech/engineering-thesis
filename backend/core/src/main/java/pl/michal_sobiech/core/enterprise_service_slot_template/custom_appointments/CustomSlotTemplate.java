package pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;

import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;

public record CustomSlotTemplate(

        long enterpriseServiceSlotId,

        long enterpriseServiceId,

        DayOfWeek dayOfWeek,

        LocalTime startTime,

        LocalTime endTime

) {

    public static CustomSlotTemplate from(EnterpriseServiceSlotTemplateEntity entity) {
        if (entity.getMaxOccupancy() != null) {
            throw new IllegalArgumentException();
        }

        return new CustomSlotTemplate(
                entity.getEnterpriseServiceSlotTemplateId(),
                entity.getEnterpriseServiceId(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime());
    }

}
