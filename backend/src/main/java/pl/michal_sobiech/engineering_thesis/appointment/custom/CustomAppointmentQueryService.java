package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;
import pl.michal_sobiech.engineering_thesis.appointment.query.AppointmentQueryTimeRange;
import pl.michal_sobiech.engineering_thesis.appointment.query.CustomAppointmentStatus;

@Service
@RequiredArgsConstructor
public class CustomAppointmentQueryService {

    private final AppointmentRepository appointmentRepository;

    public List<CustomAppointment> getCustomerCustomAppointments(
            long customerUserId,
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
                customerUserId,
                null,
                null,
                isCancelled.orElse(null),
                futureVsPast,
                acceptedVsRejected)
                .stream()
                .map(CustomAppointmentFactory::fromEntity)
                .collect(Collectors.toList());
    }

}
