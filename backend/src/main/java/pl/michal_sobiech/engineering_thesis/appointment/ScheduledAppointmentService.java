package pl.michal_sobiech.engineering_thesis.appointment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentQueryService;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointmentQueryService;

@Service
@RequiredArgsConstructor
public class ScheduledAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CustomAppointmentQueryService customAppointmentQueryService;
    private final NonCustomAppointmentQueryService nonCustomAppointmentQueryService;

    public List<ScheduledAppointment> getCustomerUncancelledFutureScheduledAppointments(long customerUserId) {
        List<ScheduledAppointment> nonCustom = nonCustomAppointmentQueryService
                .getCustomerUncancelledFutureScheduledAppointments(customerUserId);

        List<ScheduledAppointment> custom = customAppointmentQueryService
                .getCustomerUncancelledFutureScheduledAppointments(customerUserId);

        List<ScheduledAppointment> all = new ArrayList<>();
        all.addAll(nonCustom);
        all.addAll(custom);

        return all;
    }

    public List<ScheduledAppointment> getPastScheduledAppointmentsOfCustomer(long customerUserId) {
        List<ScheduledAppointment> nonCustom = nonCustomAppointmentQueryService
                .getCustomerUncancelledPastScheduledAppointments(customerUserId);

        List<ScheduledAppointment> custom = customAppointmentQueryService
                .getCustomerUncancelledPastScheduledAppointments(customerUserId);

        List<ScheduledAppointment> all = new ArrayList<>();
        all.addAll(nonCustom);
        all.addAll(custom);

        return all;
    }

    public void cancelAppointment(long appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setCancelled(true);
        appointmentRepository.save(appointment);
    }

}
