package pl.michal_sobiech.infra.enterprise_service_slot_template;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;

@Component
@RequiredArgsConstructor
public class EnterpriseServiceSlotTemplateRepositoryImpl implements EnterpriseServiceSlotTemplateRepository {

    private final SpringEnterpriseServiceSlotTemplateRepository springEnterpriseServiceSlotTemplateRepository;

    @Override
    public EnterpriseServiceSlotTemplateEntity save(EnterpriseServiceSlotTemplateEntity record) {
        return springEnterpriseServiceSlotTemplateRepository.save(record);
    }

    @Override
    public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceId(long enterpriseServiceId) {
        return springEnterpriseServiceSlotTemplateRepository.findAllByEnterpriseServiceId(enterpriseServiceId);
    }

    @Override
    public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceIdAndDayOfWeek(
            long enterpriseServiceId,
            DayOfWeek dayOfWeek) {
        return springEnterpriseServiceSlotTemplateRepository
                .findAllByEnterpriseServiceIdAndDayOfWeek(enterpriseServiceId, dayOfWeek);
    }

    @Override
    public void deleteAllByEnterpriseServiceId(long enterpriseServiceId) {
        springEnterpriseServiceSlotTemplateRepository.deleteAllByEnterpriseServiceId(enterpriseServiceId);
    }

}
