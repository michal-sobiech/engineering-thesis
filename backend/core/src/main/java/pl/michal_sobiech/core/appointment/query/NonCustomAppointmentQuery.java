package pl.michal_sobiech.core.appointment.query;

import java.util.Optional;

public record NonCustomAppointmentQuery(
        Optional<Boolean> isCancelled,
        Optional<AppointmentQueryTimeRange> timeRange) {
}
