package pl.michal_sobiech.engineering_thesis.appointment.scheduled.future;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class FutureScheduledAppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<GetEnterpriseServiceFutureScheduledAppointmentsResponseRow> getUncancelledFutureScheduledAppointmentsOfEnterpriseService(
            long enterpriseServiceId) {
        return appointmentRepository
                .findUncancelledFutureScheduledAppointmentsOfEnterpriseService(enterpriseServiceId);
    }

}