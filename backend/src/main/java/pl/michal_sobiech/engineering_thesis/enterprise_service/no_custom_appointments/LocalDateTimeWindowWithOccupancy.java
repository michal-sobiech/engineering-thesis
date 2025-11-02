package pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments;

import java.time.LocalDateTime;

public record LocalDateTimeWindowWithOccupancy(

        LocalDateTime start,
        LocalDateTime end,
        int maxOccupancy

) {

    public LocalDateTimeWindowWithOccupancy {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End must be after start");
        }
    }

}
