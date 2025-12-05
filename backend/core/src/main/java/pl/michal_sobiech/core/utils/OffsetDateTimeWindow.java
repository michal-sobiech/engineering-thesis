package pl.michal_sobiech.core.utils;

import java.time.OffsetDateTime;

public record OffsetDateTimeWindow(

        OffsetDateTime start,
        OffsetDateTime end

) {
}
