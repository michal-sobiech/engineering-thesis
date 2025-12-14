package pl.michal_sobiech.core.utils.instant_window;

import java.time.Instant;

public record SimpleInstantWindow(

        Instant start,

        Instant end

) implements InstantWindow {

    public SimpleInstantWindow {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End must be after start");
        }
    }

}