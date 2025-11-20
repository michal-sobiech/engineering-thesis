package pl.michal_sobiech.engineering_thesis.appointment.query;

import java.util.Optional;

public record NonCustomAppointmentQuery(
        Optional<Boolean> isCancelled,
        Optional<AppointmentQueryTimeRange> timeRange) {
}
