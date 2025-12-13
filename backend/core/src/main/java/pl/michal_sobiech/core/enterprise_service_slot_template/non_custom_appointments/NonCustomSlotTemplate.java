package pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;

import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.core.utils.local_time_window.LocalTimeWindow;

public record NonCustomSlotTemplate(

        long enterpriseServiceSlotId,

        long enterpriseServiceId,

        DayOfWeek dayOfWeek,

        LocalTime start,

        LocalTime end,

        short maxOccupancy

) implements LocalTimeWindow {

    public static NonCustomSlotTemplate from(EnterpriseServiceSlotTemplateEntity entity) {
        if (entity.getMaxOccupancy() == null) {
            throw new IllegalStateException();
        }

        return new NonCustomSlotTemplate(
                entity.getEnterpriseServiceSlotTemplateId(),
                entity.getEnterpriseServiceId(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getMaxOccupancy());
    }

}
