import { LocalTime } from "@js-joda/core";
import { TimeWindow as SwaggerTimeWindow } from "../GENERATED-api";
import { WeeklyTimeWindow } from "../utils/WeeklyTimeWindow";
import { jodaDayOfWeekToSwagger, swaggerDayOfWeekToJoda } from "./day-of-week-mapper";

export function swaggerTimeWindowToWeeklyTimeWindow(swaggerTimeWindow: SwaggerTimeWindow): WeeklyTimeWindow {
    return {
        dayOfWeek: swaggerDayOfWeekToJoda(swaggerTimeWindow.dayOfWeek),
        start: LocalTime.parse(swaggerTimeWindow.startTime),
        end: LocalTime.parse(swaggerTimeWindow.endTime),
    };
}

export function weeklyTimeWindowToSwagger(domainTimeWindow: WeeklyTimeWindow): SwaggerTimeWindow {
    return {
        dayOfWeek: jodaDayOfWeekToSwagger(domainTimeWindow.dayOfWeek),
        startTime: domainTimeWindow.start.toString(),
        endTime: domainTimeWindow.end.toString()
    };
}