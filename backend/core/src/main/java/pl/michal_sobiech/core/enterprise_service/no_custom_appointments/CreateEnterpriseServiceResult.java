
package pl.michal_sobiech.core.enterprise_service.no_custom_appointments;

import java.util.List;

import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;

public record CreateEnterpriseServiceResult(

        EnterpriseServiceEntity service,

        List<EnterpriseServiceSlotTemplateEntity> slots

) {
}
