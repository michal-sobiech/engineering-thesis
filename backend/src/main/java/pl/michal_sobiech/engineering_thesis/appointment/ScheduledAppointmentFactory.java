package pl.michal_sobiech.engineering_thesis.appointment;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.ScheduledAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointment;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceDomain;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;

@Service
@RequiredArgsConstructor
public class ScheduledAppointmentFactory {

    private final EnterpriseServiceService enterpriseServiceService;

    public ScheduledAppointment fromNonCustom(NonCustomAppointment appointment) {
        EnterpriseServiceDomain enterpriseService = enterpriseServiceService.getById(appointment.enterpriseServiceId())
                .orElseThrow();

        return new ScheduledAppointment(
                appointment.appointmentId(),
                appointment.enterpriseServiceId(),
                appointment.customerUserId(),
                appointment.price(),
                appointment.startInstant(),
                appointment.endInstant(),
                enterpriseService.location());
    }

    public ScheduledAppointment fromConfirmedCustom(ScheduledAppointment appointment) {
        return new ScheduledAppointment(
                appointment.appointmentId(),
                appointment.enterpriseServiceId(),
                appointment.customerUserId(),
                appointment.price(),
                appointment.startInstant(),
                appointment.endInstant(),
                appointment.location());
    }

}
