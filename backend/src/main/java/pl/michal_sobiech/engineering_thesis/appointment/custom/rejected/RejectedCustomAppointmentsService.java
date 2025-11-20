package pl.michal_sobiech.engineering_thesis.appointment.custom.rejected;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class RejectedCustomAppointmentsService {

    private final AppointmentRepository appointmentRepository;

    public List<RejectedCustomAppointment> getRejectedCustomAppointmentsOfCustomer(long customerUserId) {
        List<AppointmentEntity> records = appointmentRepository
                .findRejectedCustomAppointmentsOfCustomer(customerUserId);
        return records
                .stream()
                .map(RejectedCustomAppointment::fromEntity)
                .collect(Collectors.toList());
    }

}
