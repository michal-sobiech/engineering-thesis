package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseServiceSlotTemplateRepository
                extends JpaRepository<EnterpriseServiceSlotTemplateEntity, Long> {

        public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceId(long enterpriseServiceId);

        public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceIdAndDayOfWeek(
                        long enterpriseServiceId,
                        DayOfWeek dayOfWeek);

}
