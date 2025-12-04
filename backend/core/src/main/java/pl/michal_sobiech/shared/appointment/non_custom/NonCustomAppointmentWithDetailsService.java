package pl.michal_sobiech.shared.appointment.non_custom;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.appointment.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class NonCustomAppointmentWithDetailsService {

    private final AppointmentRepository appointmentRepository;

}
