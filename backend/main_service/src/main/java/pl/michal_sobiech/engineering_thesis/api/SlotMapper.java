package pl.michal_sobiech.engineering_thesis.api;

import java.math.BigDecimal;
import java.time.LocalTime;

import org.SwaggerCodeGenExample.model.Slot;

import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.CreateSlotTemplateCommand;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplate;
import pl.michal_sobiech.core.utils.DayOfWeekUtils;

public class SlotMapper {

    public static CreateSlotTemplateCommand fromSwaggerSlot(Slot slot) {
        LocalTime startTime = LocalTime.parse(slot.getStartTime());
        LocalTime endTime = LocalTime.parse(slot.getEndTime());
        return new CreateSlotTemplateCommand(
                DayOfWeekUtils.swaggerToStdDayOfWeek(slot.getDayOfWeek()),
                startTime,
                endTime,
                slot.getMaxOccupancy().shortValue());
    }

    public static Slot fromNonCustomSlotTemplate(NonCustomSlotTemplate nonCustomSlotTemplate) {
        return new Slot(
                DayOfWeekUtils.stdDayOfWeekToSwagger(nonCustomSlotTemplate.dayOfWeek()),
                nonCustomSlotTemplate.start().toString(),
                nonCustomSlotTemplate.end().toString(),
                BigDecimal.valueOf(nonCustomSlotTemplate.maxOccupancy()));
    }

}
