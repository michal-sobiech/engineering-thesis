
package pl.michal_sobiech.engineering_thesis.enterprise_service.custom_appointments;

import java.util.List;

import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.custom_appointments.CustomAppointmentsEnterpriseServiceSlotTemplate;

public record CreateCustomAppointmentsEnterpriseServiceResult(

        CustomAppointmentsService service,

        List<CustomAppointmentsEnterpriseServiceSlotTemplate> slots

) {
}
