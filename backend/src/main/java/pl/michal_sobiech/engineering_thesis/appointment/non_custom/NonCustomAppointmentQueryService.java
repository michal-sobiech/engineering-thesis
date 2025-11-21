package pl.michal_sobiech.engineering_thesis.appointment.non_custom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;
import pl.michal_sobiech.engineering_thesis.appointment.custom.ScheduledAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.query.AppointmentQueryTimeRange;

@Service
@RequiredArgsConstructor
public class NonCustomAppointmentQueryService {

    private final AppointmentRepository appointmentRepository;

    public List<AppointmentEntity> getNonCustomAppointments(
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

    public List<ScheduledAppointment> getCustomerUncancelledFutureScheduledCustomAppointments(long customerUserId) {
        return getNonCustomAppointments(
                Optional.of(customerUserId),
                Optional.empty(),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.FUTURE))
                .stream()
                .map(ScheduledAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

    public List<ScheduledAppointment> getCustomerUncancelledPastScheduledCustomAppointments(long customerUserId) {
        return getNonCustomAppointments(
                Optional.of(customerUserId),
                Optional.empty(),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.PAST))
                .stream()
                .map(ScheduledAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

}
