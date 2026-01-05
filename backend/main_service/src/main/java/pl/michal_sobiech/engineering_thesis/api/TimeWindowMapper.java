package pl.michal_sobiech.engineering_thesis.api;

import java.time.LocalTime;

import org.SwaggerCodeGenExample.model.TimeWindow;

import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CreateTimeWindowTemplateCommand;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomSlotTemplate;

public class TimeWindowMapper {

    public static CreateTimeWindowTemplateCommand createCommandFromSwaggerTimeWindow(
            TimeWindow swaggerTimeWindow) {
        LocalTime startTime = LocalTime.parse(swaggerTimeWindow.getStartTime());
        LocalTime endTime = LocalTime.parse(swaggerTimeWindow.getEndTime());
        return new CreateTimeWindowTemplateCommand(
                DayOfWeekMapper.swaggerToStdDayOfWeek(swaggerTimeWindow.getDayOfWeek()),
                startTime,
                endTime);
    }

    public static TimeWindow fromCustomSlotTemplate(CustomSlotTemplate customSlotTemplate) {
        return new TimeWindow(
                DayOfWeekMapper.stdDayOfWeekToSwagger(customSlotTemplate.dayOfWeek()),
                customSlotTemplate.start().toString(),
                customSlotTemplate.end().toString());
    }

}
