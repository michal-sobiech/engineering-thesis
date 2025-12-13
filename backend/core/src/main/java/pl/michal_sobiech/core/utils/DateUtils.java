package pl.michal_sobiech.core.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindow;

public class DateUtils {

    public static ZonedDateTime createZonedDateTime(ZonedDate date, LocalTime time) {
        return ZonedDateTime.of(date.date(), time, date.timeZone());
    }

    public static List<LocalDate> getAllDatesBetweenIncludingBorders(LocalDate from, LocalDate to) {
        return from.datesUntil(to.plusDays(1)).collect(Collectors.toList());
    }

    public static OffsetDateTime createOffsetDateTime(LocalDate date, ZoneId timeZone) {
        return date.atStartOfDay(timeZone).toOffsetDateTime();
    }

    public static OffsetDateTime createOffsetDateTime(LocalDateTime date, ZoneId timeZone) {
        return date.atZone(timeZone).toOffsetDateTime();
    }

    public static OffsetDateTime createOffsetDateTimeWithResetTime(OffsetDateTime datetime) {
        return datetime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime createLocalDateTime(OffsetDateTime offsetDatetime, ZoneId timezone) {
        return offsetDatetime.atZoneSameInstant(timezone).toLocalDateTime();
    }

    public static LocalDateTime createLocalDateTime(Instant instant, ZoneId timezone) {
        return instant.atZone(timezone).toLocalDateTime();
    }

    public static LocalDate createLocalDate(OffsetDateTime offsetDatetime, ZoneId timezone) {
        LocalDateTime datetime = createLocalDateTime(offsetDatetime, timezone);
        return datetime.toLocalDate();
    }

    public static String extractHHmmTimeFromLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static Instant createInstant(LocalDateTime localDatetime, ZoneId timezone) {
        return createOffsetDateTime(localDatetime, timezone).toInstant();
    }

    public static String createIsoLocalDatetime(Instant instant, ZoneId timezone) {
        return instant.atZone(timezone).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static OffsetDateTimeWindow createOffsetDateTimeWindow(LocalDatetimeWindow localWindow, ZoneId timezone) {
        return new OffsetDateTimeWindow(
                DateUtils.createOffsetDateTime(localWindow.start(), timezone),
                DateUtils.createOffsetDateTime(localWindow.end(), timezone));
    }

}
