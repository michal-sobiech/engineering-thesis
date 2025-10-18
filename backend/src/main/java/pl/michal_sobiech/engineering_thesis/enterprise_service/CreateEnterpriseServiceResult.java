
package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.List;

import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlot;

public record CreateEnterpriseServiceResult(

        EnterpriseService service,

        List<EnterpriseServiceSlot> slots

) {
}
