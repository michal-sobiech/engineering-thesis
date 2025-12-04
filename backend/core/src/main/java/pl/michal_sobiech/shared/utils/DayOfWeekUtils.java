package pl.michal_sobiech.engineering_thesis.utils;

public class DayOfWeekUtils {
    public static java.time.DayOfWeek swaggerToStdDayOfWeek(
            org.SwaggerCodeGenExample.model.DayOfWeek swaggerDayOfWeek) {
        switch (swaggerDayOfWeek) {
            case org.SwaggerCodeGenExample.model.DayOfWeek.MONDAY:
                return java.time.DayOfWeek.MONDAY;
            case org.SwaggerCodeGenExample.model.DayOfWeek.TUESDAY:
                return java.time.DayOfWeek.TUESDAY;
            case org.SwaggerCodeGenExample.model.DayOfWeek.WEDNESDAY:
                return java.time.DayOfWeek.WEDNESDAY;
            case org.SwaggerCodeGenExample.model.DayOfWeek.THURSDAY:
                return java.time.DayOfWeek.THURSDAY;
            case org.SwaggerCodeGenExample.model.DayOfWeek.FRIDAY:
                return java.time.DayOfWeek.FRIDAY;
            case org.SwaggerCodeGenExample.model.DayOfWeek.SATURDAY:
                return java.time.DayOfWeek.SATURDAY;
            case org.SwaggerCodeGenExample.model.DayOfWeek.SUNDAY:
                return java.time.DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static short getNumberOfDayOfWeek(java.time.DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> 1;
            case TUESDAY -> 2;
            case WEDNESDAY -> 3;
            case THURSDAY -> 4;
            case FRIDAY -> 5;
            case SATURDAY -> 6;
            case SUNDAY -> 7;
        };
    }

    public static java.time.DayOfWeek getDayOfWeek(short number) {
        return switch (number) {
            case 1 -> java.time.DayOfWeek.MONDAY;
            case 2 -> java.time.DayOfWeek.TUESDAY;
            case 3 -> java.time.DayOfWeek.WEDNESDAY;
            case 4 -> java.time.DayOfWeek.THURSDAY;
            case 5 -> java.time.DayOfWeek.FRIDAY;
            case 6 -> java.time.DayOfWeek.SATURDAY;
            case 7 -> java.time.DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException();
        };
    }
}
