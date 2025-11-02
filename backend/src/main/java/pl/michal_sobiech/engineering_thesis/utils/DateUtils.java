package pl.michal_sobiech.engineering_thesis.utils;

import java.time.LocalTime;
import java.time.ZonedDateTime;

public class DateUtils {

    public static ZonedDateTime fromZonedDateAndLocalTime(ZonedDate date, LocalTime time) {
        return ZonedDateTime.of(date.date(), time, date.timeZone());
    }

}
