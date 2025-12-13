import { LocalTime } from "@js-joda/core";
import { Slot as SwaggerSlot } from "../GENERATED-api";
import { WeeklyTimeWindowWithCapacity } from "../utils/WeeklyTimeWindowWithCapacity";
import { jodaDayOfWeekToSwagger, swaggerDayOfWeekToJoda } from "./day-of-week-mapper";

export function swaggerSlotToWeeklyTimeWindowWithCapacity(swaggerSlot: SwaggerSlot): WeeklyTimeWindowWithCapacity {
    return {
        dayOfWeek: swaggerDayOfWeekToJoda(swaggerSlot.dayOfWeek),
        start: LocalTime.parse(swaggerSlot.startTime),
        end: LocalTime.parse(swaggerSlot.endTime),
        capacity: swaggerSlot.maxOccupancy,
    };
}

export function weeklyTimeWindowWithCapacityToSwaggerSlot(domainSlot: WeeklyTimeWindowWithCapacity): SwaggerSlot {
    return {
        dayOfWeek: jodaDayOfWeekToSwagger(domainSlot.dayOfWeek),
        startTime: domainSlot.start.toString(),
        endTime: domainSlot.end.toString(),
        maxOccupancy: domainSlot.capacity,
    };
}