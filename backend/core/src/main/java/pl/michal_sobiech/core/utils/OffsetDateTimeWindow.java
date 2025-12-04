package pl.michal_sobiech.shared.utils;

import java.time.OffsetDateTime;

public record OffsetDateTimeWindow(

        OffsetDateTime start,
        OffsetDateTime end

) {
}
