package pl.michal_sobiech.engineering_thesis.appointment.scheduled.future;

import java.util.ArrayList;
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
        List<GetEnterpriseServiceFutureScheduledAppointmentsResponseRow> out = new ArrayList<>();

        var futureScheduledNonCustomAppointments = appointmentRepository
                .getUncancelledFutureScheduledNonCustomAppointmentsOfEnterpriseService(enterpriseServiceId);
        var futureScheduledCustomAppointments = appointmentRepository
                .getUncancelledFutureScheduledCustomAppointmentsOfEnterpriseService(enterpriseServiceId);

        out.addAll(futureScheduledNonCustomAppointments);
        out.addAll(futureScheduledCustomAppointments);

        return out;
    }

}