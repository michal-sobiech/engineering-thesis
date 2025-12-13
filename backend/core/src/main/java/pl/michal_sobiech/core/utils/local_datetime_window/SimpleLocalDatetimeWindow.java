package pl.michal_sobiech.core.utils.local_datetime_window;

import java.time.LocalDateTime;

public record SimpleLocalDatetimeWindow(

        LocalDateTime start,

        LocalDateTime end

) implements LocalDatetimeWindow {

    public SimpleLocalDatetimeWindow {
        if (!end.isAfter(start)) {
            throw new IllegalStateException("End must be after start");
        }
    }

}
