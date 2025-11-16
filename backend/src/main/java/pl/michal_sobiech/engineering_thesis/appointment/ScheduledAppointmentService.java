package pl.michal_sobiech.engineering_thesis.appointment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.ConfirmedCustomAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointmentsService;

@Service
@RequiredArgsConstructor
public class ScheduledAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CustomAppointmentsService customAppointmentsService;
    private final NonCustomAppointmentsService nonCustomAppointmentsService;
    private final ScheduledAppointmentFactory scheduledAppointmentFactory;

    public List<ScheduledAppointment> getScheduledAppointmentsOfCustomer(long customerUserId) {
        List<ConfirmedCustomAppointment> confirmedCustomAppointments = customAppointmentsService
                .getConfirmedCustomAppointmentsOfCustomer(customerUserId);
        List<ScheduledAppointment> scheduledConfirmedCustomAppointments = confirmedCustomAppointments.stream()
                .map(scheduledAppointmentFactory::fromConfirmedCustom)
                .collect(Collectors.toList());

        List<NonCustomAppointment> nonCustomAppointments = nonCustomAppointmentsService
                .getNonCustomAppointmentsOfCustomer(customerUserId);
        List<ScheduledAppointment> scheduledNonCustomAppointments = nonCustomAppointments.stream()
                .map(scheduledAppointmentFactory::fromNonCustom)
                .collect(Collectors.toList());

        List<ScheduledAppointment> all = new ArrayList<>();
        all.addAll(scheduledConfirmedCustomAppointments);
        all.addAll(scheduledNonCustomAppointments);

        return all;
    }

    public List<ScheduledAppointment> getPastScheduledAppointmentsOfCustomer(long customerUserId) {
        List<ScheduledAppointment> all = getScheduledAppointmentsOfCustomer(customerUserId);
        Instant now = Instant.now();
        return all.stream()
                .filter(appointment -> appointment.endInstant().isAfter(now))
                .collect(Collectors.toList());
    }

    public List<ScheduledAppointment> getFutureScheduledAppointmentsOfCustomer(long customerUserId) {
        List<ScheduledAppointment> all = getScheduledAppointmentsOfCustomer(customerUserId);
        Instant now = Instant.now();
        return all.stream()
                .filter(appointment -> appointment.startInstant().isAfter(now))
                .collect(Collectors.toList());
    }

    public void cancelAppointment(long appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setCancelled(true);
        appointmentRepository.save(appointment);
    }

}
