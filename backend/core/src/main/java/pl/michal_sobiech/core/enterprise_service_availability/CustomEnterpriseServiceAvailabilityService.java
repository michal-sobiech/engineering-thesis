package pl.michal_sobiech.core.enterprise_service_availability;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.custom.CustomAppointmentService;
import pl.michal_sobiech.core.enterprise_service_default_availability.custom.CustomEnterpriseServiceDefaultAvailabilityService;
import pl.michal_sobiech.core.utils.instant_window.InstantWindow;
import pl.michal_sobiech.core.utils.instant_window.InstantWindowUtils;
import pl.michal_sobiech.core.utils.instant_window.SimpleInstantWindow;

@RequiredArgsConstructor
public class CustomEnterpriseServiceAvailabilityService {

    private final CustomAppointmentService customAppointmentService;
    private final CustomEnterpriseServiceDefaultAvailabilityService defaultAvailabilityService;

    public List<InstantWindow> calcServiceAvailability(
            long enterpriseServiceId,
            Instant from,
            Instant to) {
        // 1. Get availability template
        // 2. Get appointments in datetime range
        // 3. Subtract

        // 1.
        List<InstantWindow> defaultAvailability = defaultAvailabilityService
                .getDefaultServiceAvailabilityInInstantRange(enterpriseServiceId, from, to);

        // 2.
        List<InstantWindow> confirmedAppointmentWindows = customAppointmentService
                .getConfirmedAppointmentsInDatetimeRange(enterpriseServiceId, from, to)
                .stream()
                .map(appointment -> new SimpleInstantWindow(appointment.startInstant(), appointment.endInstant()))
                .collect(Collectors.toList());

        // 3.
        return InstantWindowUtils.subtractTimeWindowLists(defaultAvailability, confirmedAppointmentWindows);
    }

}
