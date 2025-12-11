import { LocalTime } from "@js-joda/core";
import { TimeWindow } from "../common/TimeWindow";
import { TimeWindow as SwaggerTimeWindow } from "../GENERATED-api";
import { jodaDayOfWeekToSwagger, swaggerDayOfWeekToJoda } from "./day-of-week-mapper";

export function swaggerTimeWindowToDomain(swaggerTimeWindow: SwaggerTimeWindow): TimeWindow {
    return {
        dayOfWeek: swaggerDayOfWeekToJoda(swaggerTimeWindow.dayOfWeek),
        startTime: LocalTime.parse(swaggerTimeWindow.startTime),
        endTime: LocalTime.parse(swaggerTimeWindow.endTime),
    };
}

export function domainTimeWindowToSwagger(domainTimeWindow: TimeWindow): SwaggerTimeWindow {
    return {
        dayOfWeek: jodaDayOfWeekToSwagger(domainTimeWindow.dayOfWeek),
        startTime: domainTimeWindow.startTime.toString(),
        endTime: domainTimeWindow.endTime.toString()
    };
}