package pl.michal_sobiech.core.utils.local_datetime_window;

import java.time.LocalDateTime;

public record LocalDatetimeWindowWithCapacity(

        LocalDateTime start,

        LocalDateTime end,

        int capacity

) implements LocalDatetimeWindow {

}
