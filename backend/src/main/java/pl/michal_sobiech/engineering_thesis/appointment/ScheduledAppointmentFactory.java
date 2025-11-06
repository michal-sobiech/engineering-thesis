package pl.michal_sobiech.engineering_thesis.appointment;

import org.SwaggerCodeGenExample.model.Location;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.ConfirmedCustomAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointment;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;

@Service
@RequiredArgsConstructor
public class ScheduledAppointmentFactory {

    private final EnterpriseServiceService enterpriseServiceService;

    public ScheduledAppointment fromNonCustom(NonCustomAppointment appointment) {
        EnterpriseServiceEntity enterpriseService = enterpriseServiceService
                .findById(appointment.enterpriseServiceId())
                .orElseThrow();

        Location location = new Location(
                enterpriseService.getAddress(),
                enterpriseService.getLongitude(),
                enterpriseService.getLatitude());

        return new ScheduledAppointment(
                appointment.appointmentId(),
                appointment.enterpriseServiceId(),
                appointment.customerUserId(),
                appointment.price(),
                appointment.startInstant(),
                appointment.endInstant(),
                location);
    }

    public ScheduledAppointment fromConfirmedCustom(ConfirmedCustomAppointment appointment) {
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
