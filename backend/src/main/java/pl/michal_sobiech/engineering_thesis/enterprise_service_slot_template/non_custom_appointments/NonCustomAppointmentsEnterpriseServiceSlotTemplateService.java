package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;

@Service
@RequiredArgsConstructor
public class NonCustomAppointmentsEnterpriseServiceSlotTemplateService {

    private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository;
    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;

    public List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> getAllByEnterpriseServiceIdAndDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotTemplateEntity> records = enterpriseServiceSlotTemplateRepository
                .findAllByEnterpriseServiceIdAndDayOfWeek(enterpiseServiceId,
                        dayOfWeek);
        return records.stream().map(record -> NonCustomAppointmentsEnterpriseServiceSlotTemplate.from(record)).toList();
    }

    public List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> getAvailabilityTemplateForDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotTemplateEntity> slots = enterpriseServiceSlotTemplateService
                .getAvailabilityTemplateForDayOfWeek(enterpiseServiceId, dayOfWeek);
        return slots.stream().map(slot -> NonCustomAppointmentsEnterpriseServiceSlotTemplate.from(slot)).toList();
    }

}
