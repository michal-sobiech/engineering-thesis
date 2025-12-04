package pl.michal_sobiech.shared.appointment.custom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.appointment.AppointmentEntity;
import pl.michal_sobiech.shared.appointment.AppointmentRepository;
import pl.michal_sobiech.shared.appointment.UncancelledScheduledAppointment;
import pl.michal_sobiech.shared.appointment.query.AppointmentQueryTimeRange;
import pl.michal_sobiech.shared.appointment.query.CustomAppointmentStatus;


@RequiredArgsConstructor
public class CustomAppointmentQueryService {

    private final AppointmentRepository appointmentRepository;

    public List<AppointmentEntity> getAppointments(
            Optional<Long> customerUserId,
            Optional<Long> enterpriseServiceId,
            Optional<Long> enterpriseId,
            Optional<Boolean> isCancelled,
            Optional<AppointmentQueryTimeRange> timeRange,
            CustomAppointmentStatus status) {

        Boolean futureVsPast;
        if (timeRange.isEmpty()) {
            futureVsPast = null;
        } else if (timeRange.get().equals(AppointmentQueryTimeRange.FUTURE)) {
            futureVsPast = true;
        } else {
            futureVsPast = false;
        }

        Boolean acceptedVsRejected = switch (status) {
            case CustomAppointmentStatus.PENDING -> null;
            case CustomAppointmentStatus.ACCEPTED -> true;
            case CustomAppointmentStatus.REJECTED -> false;
        };

        return appointmentRepository.findCustomAppointments(
                customerUserId.orElse(null),
                enterpriseServiceId.orElse(null),
                enterpriseId.orElse(null),
                isCancelled.orElse(null),
                futureVsPast,
                acceptedVsRejected);
    }

    public List<UncancelledPendingAppointment> getCustomerUncancelledFuturePendingAppointments(
            long customerUserId) {
        return getAppointments(
                Optional.of(customerUserId),
                Optional.empty(),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.FUTURE),
                CustomAppointmentStatus.PENDING)
                .stream()
                .map(UncancelledPendingAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

    public List<RejectedAppointment> getCustomerFutureRejectedAppointments(long customerUserId) {
        return getAppointments(
                Optional.of(customerUserId),
                Optional.empty(),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.FUTURE),
                CustomAppointmentStatus.REJECTED)
                .stream()
                .map(RejectedAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

    public List<UncancelledScheduledAppointment> getCustomerUncancelledFutureScheduledAppointments(
            long customerUserId) {
        return getAppointments(
                Optional.of(customerUserId),
                Optional.empty(),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.FUTURE),
                CustomAppointmentStatus.ACCEPTED)
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
                Optional.of(AppointmentQueryTimeRange.PAST),
                CustomAppointmentStatus.ACCEPTED)
                .stream()
                .map(UncancelledScheduledAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

    public List<UncancelledPendingAppointment> getEnterpriseServiceUncancelledFuturePendingAppointments(
            long enterpriseServiceId) {
        return getAppointments(
                Optional.empty(),
                Optional.of(enterpriseServiceId),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.FUTURE),
                CustomAppointmentStatus.PENDING)
                .stream()
                .map(UncancelledPendingAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }
}
