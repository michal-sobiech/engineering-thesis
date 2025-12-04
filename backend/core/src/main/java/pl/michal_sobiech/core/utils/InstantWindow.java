package pl.michal_sobiech.engineering_thesis.utils;

import java.time.Instant;

public record InstantWindow(

        Instant start,

        Instant end

) {

    public InstantWindow {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End must be after start");
        }
    }

}