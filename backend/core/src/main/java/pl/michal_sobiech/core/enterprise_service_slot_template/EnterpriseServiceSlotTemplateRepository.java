package pl.michal_sobiech.core.enterprise_service_slot_template;

import java.time.DayOfWeek;
import java.util.List;

public interface EnterpriseServiceSlotTemplateRepository {

    public EnterpriseServiceSlotTemplateEntity save(EnterpriseServiceSlotTemplateEntity record);

    public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceId(long enterpriseServiceId);

    public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceIdAndDayOfWeek(
            long enterpriseServiceId,
            DayOfWeek dayOfWeek);

}
