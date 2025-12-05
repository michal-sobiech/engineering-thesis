package pl.michal_sobiech.infra.enterprise_service_slot_template;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;

public interface SpringEnterpriseServiceSlotTemplateRepository
        extends JpaRepository<EnterpriseServiceSlotTemplateEntity, Long> {

    public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceId(long enterpriseServiceId);

    public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceIdAndDayOfWeek(
            long enterpriseServiceId,
            DayOfWeek dayOfWeek);

}
