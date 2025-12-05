package pl.michal_sobiech.core.enterprise_service_slot_template;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnterpriseServiceSlotTemplateService {

    private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository;

    public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceId(long enterpriseServiceId) {
        return enterpriseServiceSlotTemplateRepository.findAllByEnterpriseServiceId(enterpriseServiceId);
    }

    public List<EnterpriseServiceSlotTemplateEntity> getAvailabilityTemplateForDayOfWeek(long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        return enterpriseServiceSlotTemplateRepository.findAllByEnterpriseServiceIdAndDayOfWeek(enterpiseServiceId,
                dayOfWeek);
    }

}
