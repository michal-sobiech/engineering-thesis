package pl.michal_sobiech.engineering_thesis.appointment.custom.pending;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class PendingAppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<GetEnterpriseServiceUncancelledFuturePendingAppointmentsResponseRow> getEnterpriseServiceUncancelledFuturePendingAppointments(
            long enterpriseServiceId) {
        return appointmentRepository.getEnterpriseServiceUncancelledFuturePendingAppointments(enterpriseServiceId);
    }

}
