package pl.michal_sobiech.engineering_thesis.appointment;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;

@Service
@RequiredArgsConstructor
public class ScheduledAppointmentFactory {

    private final EnterpriseServiceService enterpriseServiceService;

    // public ScheduledAppointment fromNonCustom(NonCustomAppointment appointment) {
    // EnterpriseServiceDomain enterpriseService =
    // enterpriseServiceService.getById(appointment.enterpriseServiceId())
    // .orElseThrow();

    // return new ScheduledAppointment(
    // appointment.appointmentId(),
    // appointment.enterpriseServiceId(),
    // appointment.customerUserId(),
    // appointment.price(),

    // appointment.startInstant(),
    // appointment.endInstant(),
    // enterpriseService.location());
    // }

}
