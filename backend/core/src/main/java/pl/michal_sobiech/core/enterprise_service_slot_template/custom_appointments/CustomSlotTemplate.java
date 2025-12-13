package pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;

import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.core.utils.local_time_window.LocalTimeWindow;

public record CustomSlotTemplate(

        long enterpriseServiceSlotId,

        long enterpriseServiceId,

        DayOfWeek dayOfWeek,

        LocalTime start,

        LocalTime end

) implements LocalTimeWindow {

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
