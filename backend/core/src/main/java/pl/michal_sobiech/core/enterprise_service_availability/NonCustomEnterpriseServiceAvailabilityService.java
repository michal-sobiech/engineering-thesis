package pl.michal_sobiech.core.enterprise_service_availability;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.non_custom.NonCustomAppointment;
import pl.michal_sobiech.core.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.core.enterprise_service_default_availability.non_custom.NonCustomEnterpriseServiceDefaultAvailabilityService;
import pl.michal_sobiech.core.utils.instant_window.InstantWindow;
import pl.michal_sobiech.core.utils.instant_window.InstantWindowWithCapacity;
import pl.michal_sobiech.core.utils.instant_window.InstantWindowWithCapacityUtils;
import pl.michal_sobiech.core.utils.instant_window.SimpleInstantWindow;

@RequiredArgsConstructor
public class NonCustomEnterpriseServiceAvailabilityService {

    private final NonCustomAppointmentsService nonCustomAppointmentsService;
    private final NonCustomEnterpriseServiceDefaultAvailabilityService defaultAvailabilityService;

    public List<InstantWindow> calcServiceAvailability(
            long enterpriseServiceId,
            Instant from,
            Instant to) {
        // 1. Get availability template
        // 2. Get appointments in datetime range
        // 3. Subtract

        // 1.
        List<InstantWindowWithCapacity> defaultAvailability = defaultAvailabilityService
                .getDefaultServiceAvailabilityInInstantRange(enterpriseServiceId, from, to);

        // 2.
        List<NonCustomAppointment> appointments = nonCustomAppointmentsService
                .getAllByServiceIdAndInstantRange(enterpriseServiceId, from, to);
        List<InstantWindow> appointmentWindows = appointments.stream()
                .map(appointment -> new SimpleInstantWindow(appointment.startInstant(), appointment.endInstant()))
                .collect(Collectors.toList());

        // 3.
        List<InstantWindowWithCapacity> availability = InstantWindowWithCapacityUtils
                .subtractTimeWindowsWithoutCapacity(defaultAvailability, appointmentWindows);

        return new ArrayList<>(availability);
    }

}
