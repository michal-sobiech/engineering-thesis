import { DayOfWeek } from "@js-joda/core";
import { DayOfWeek as SwaggerDayOfWeek } from "../GENERATED-api";
import { reverseMap } from "../utils/map";

const swaggerDayOfWeekToJodaMap = new Map<SwaggerDayOfWeek, DayOfWeek>([
    ["MONDAY", DayOfWeek.MONDAY],
    ["TUESDAY", DayOfWeek.TUESDAY],
    ["WEDNESDAY", DayOfWeek.WEDNESDAY],
    ["THURSDAY", DayOfWeek.THURSDAY],
    ["FRIDAY", DayOfWeek.FRIDAY],
    ["SATURDAY", DayOfWeek.SATURDAY],
    ["SUNDAY", DayOfWeek.SUNDAY],
]);

const jodaDayOfWeekToSwaggerMap: Map<DayOfWeek, SwaggerDayOfWeek> = reverseMap(swaggerDayOfWeekToJodaMap);

export function swaggerDayOfWeekToJoda(swaggerDayOfWeek: SwaggerDayOfWeek): DayOfWeek {
    const jodaDayOfWeek = swaggerDayOfWeekToJodaMap.get(swaggerDayOfWeek);
    if (jodaDayOfWeek !== undefined) {
        return jodaDayOfWeek;
    }
    throw new Error("Invalid argument");
}

export function jodaDayOfWeekToSwagger(jodaDayOfWeek: DayOfWeek): SwaggerDayOfWeek {
    const swaggerDayOfWeek = jodaDayOfWeekToSwaggerMap.get(jodaDayOfWeek);
    if (swaggerDayOfWeek !== undefined) {
        return swaggerDayOfWeek;
    }
    throw new Error("Invalid argument");
}