package pl.michal_sobiech.engineering_thesis.utils;

import java.time.LocalDateTime;

public record LocalDateTimeWindow(

        LocalDateTime start,
        LocalDateTime end

) {

    public LocalDateTimeWindow {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End must be after start");
        }
    }

}
