package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class CustomAppointmentsService {

    private final AppointmentRepository appointmentRepository;

    public List<ConfirmedCustomAppointment> getConfirmedAppointmentsInDatetimeRange(long serviceId, OffsetDateTime from,
            OffsetDateTime to) {
        List<AppointmentEntity> appointments = appointmentRepository.findConfirmedInDatetimeRange(serviceId, from, to);
        return appointments.stream().map(a -> ConfirmedCustomAppointment.fromEntity(a)).collect(Collectors.toList());
    }
}
