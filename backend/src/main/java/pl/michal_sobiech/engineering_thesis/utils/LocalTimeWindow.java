package pl.michal_sobiech.engineering_thesis.utils;

import java.time.LocalTime;

public record LocalTimeWindow(

        LocalTime start,

        LocalTime end

) {

    public LocalTimeWindow {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End must be after start");
        }
    }

}
