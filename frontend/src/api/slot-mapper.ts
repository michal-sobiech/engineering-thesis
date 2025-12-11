import { LocalTime } from "@js-joda/core";
import { Slot } from "../common/Slot";
import { Slot as SwaggerSlot } from "../GENERATED-api";
import { jodaDayOfWeekToSwagger, swaggerDayOfWeekToJoda } from "./day-of-week-mapper";

export function swaggerSlotToDomain(swaggerSlot: SwaggerSlot): Slot {
    return {
        dayOfWeek: swaggerDayOfWeekToJoda(swaggerSlot.dayOfWeek),
        startTime: LocalTime.parse(swaggerSlot.startTime),
        endTime: LocalTime.parse(swaggerSlot.endTime),
        maxOccupancy: swaggerSlot.maxOccupancy,
    };
}

export function domainSlotToSwagger(domainSlot: Slot): SwaggerSlot {
    return {
        dayOfWeek: jodaDayOfWeekToSwagger(domainSlot.dayOfWeek),
        startTime: domainSlot.startTime.toString(),
        endTime: domainSlot.endTime.toString(),
        maxOccupancy: domainSlot.maxOccupancy,
    };
}