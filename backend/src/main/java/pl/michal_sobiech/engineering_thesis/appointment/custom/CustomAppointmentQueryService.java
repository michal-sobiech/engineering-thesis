package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;
import pl.michal_sobiech.engineering_thesis.appointment.query.AppointmentQueryTimeRange;
import pl.michal_sobiech.engineering_thesis.appointment.query.CustomAppointmentStatus;

@Service
@RequiredArgsConstructor
public class CustomAppointmentQueryService {

    private final AppointmentRepository appointmentRepository;

    public List<AppointmentEntity> getCustomAppointments(
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

    public <T extends CustomAppointment> List<T> getCustomAppointments(
            Optional<Long> customerUserId,
            Optional<Long> enterpriseServiceId,
            Optional<Long> enterpriseId,
            Optional<Boolean> isCancelled,
            Optional<AppointmentQueryTimeRange> timeRange,
            CustomAppointmentStatus status,
            Function<AppointmentEntity, T> factoryFunction) {
        return getCustomAppointments(customerUserId, enterpriseServiceId, enterpriseId, isCancelled, timeRange, status)
                .stream()
                .map(factoryFunction)
                .collect(Collectors.toList());
    }

}
