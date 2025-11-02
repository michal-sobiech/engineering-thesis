
package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.List;

import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;

public record CreateEnterpriseServiceResult(

        EnterpriseServiceEntity service,

        List<EnterpriseServiceSlotTemplateEntity> slots

) {
}
