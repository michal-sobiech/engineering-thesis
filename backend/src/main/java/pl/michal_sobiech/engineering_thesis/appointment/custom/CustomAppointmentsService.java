package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;
import pl.michal_sobiech.engineering_thesis.utils.DatetimeWindow;

@Service
@RequiredArgsConstructor
public class CustomAppointmentsService {

    private final AppointmentRepository appointmentRepository;

    public List<DatetimeWindow> findTakenTimeWindowsOnDate(long serviceId, OffsetDateTime date) {
        OffsetDateTime from = date.
        List<AppointmentEntity> appointments = appointmentRepository.findTakenDatetimeWindowsInRange(serviceId, )
    }

}
