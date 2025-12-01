package pl.michal_sobiech.engineering_thesis.utils;

import java.time.LocalDate;
import java.time.ZoneId;

public record ZonedDate(

        LocalDate date,
        ZoneId timeZone

) {
}
