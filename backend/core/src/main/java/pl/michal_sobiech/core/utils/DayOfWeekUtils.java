package pl.michal_sobiech.core.utils;

public class DayOfWeekUtils {
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
