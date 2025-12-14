package pl.michal_sobiech.core.utils.instant_window;

import java.time.Instant;

public record InstantWindowWithCapacity(

        Instant start,

        Instant end,

        int capacity

) implements InstantWindow {

    public InstantWindowWithCapacity {
        if (capacity <= 0) {
            throw new IllegalStateException("Capacity must be > 0");
        }
    }

}
