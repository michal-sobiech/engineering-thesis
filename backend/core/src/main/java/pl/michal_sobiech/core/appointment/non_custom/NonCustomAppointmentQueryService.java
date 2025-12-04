package pl.michal_sobiech.shared.appointment.non_custom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.appointment.AppointmentEntity;
import pl.michal_sobiech.shared.appointment.AppointmentRepository;
import pl.michal_sobiech.shared.appointment.UncancelledScheduledAppointment;
import pl.michal_sobiech.shared.appointment.query.AppointmentQueryTimeRange;

@Service
@RequiredArgsConstructor
public class NonCustomAppointmentQueryService {

    private final AppointmentRepository appointmentRepository;

    public List<AppointmentEntity> getAppointments(
            Optional<Long> customerUserId,
            Optional<Long> enterpriseServiceId,
            Optional<Long> enterpriseId,
            Optional<Boolean> isCancelled,
            Optional<AppointmentQueryTimeRange> timeRange) {

        Boolean futureVsPast;
        if (timeRange.isEmpty()) {
            futureVsPast = null;
        } else if (timeRange.get().equals(AppointmentQueryTimeRange.FUTURE)) {
            futureVsPast = true;
        } else {
            futureVsPast = false;
        }

        return appointmentRepository.findNonCustomAppointments(
                customerUserId.orElse(null),
                enterpriseServiceId.orElse(null),
                enterpriseId.orElse(null),
                isCancelled.orElse(null),
                futureVsPast);
    }

    public List<UncancelledScheduledAppointment> getCustomerUncancelledFutureScheduledAppointments(
            long customerUserId) {
        return getAppointments(
                Optional.of(customerUserId),
                Optional.empty(),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.FUTURE))
                .stream()
                .map(UncancelledScheduledAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

    public List<UncancelledScheduledAppointment> getCustomerUncancelledPastScheduledAppointments(long customerUserId) {
        return getAppointments(
                Optional.of(customerUserId),
                Optional.empty(),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.PAST))
                .stream()
                .map(UncancelledScheduledAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

}
