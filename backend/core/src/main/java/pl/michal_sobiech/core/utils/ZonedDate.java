package pl.michal_sobiech.core.utils;

import java.time.LocalDate;
import java.time.ZoneId;

public record ZonedDate(

        LocalDate date,
        ZoneId timeZone

) {
}
