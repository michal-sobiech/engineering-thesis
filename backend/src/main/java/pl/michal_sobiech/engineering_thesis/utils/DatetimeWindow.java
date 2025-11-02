package pl.michal_sobiech.engineering_thesis.utils;

import java.time.OffsetDateTime;

public record DatetimeWindow(

        OffsetDateTime start,
        OffsetDateTime end

) {
}
