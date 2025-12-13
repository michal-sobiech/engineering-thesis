package pl.michal_sobiech.core.utils.local_time_window;

import java.time.LocalTime;

public record SimpleLocalTimeWindow(

        LocalTime start,

        LocalTime end

) implements LocalTimeWindow {

    public SimpleLocalTimeWindow {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End must be after start");
        }
    }

}
