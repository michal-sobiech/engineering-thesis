package pl.michal_sobiech.engineering_thesis.enterprise_service_availability_template.non_custom;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments.NonCustomAppointmentsEnterpriseServiceSlotTemplate;

@Service
@RequiredArgsConstructor
public class NonCustomEnterpriseServiceAvailabilityTemplateService {

    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;

    public List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> getAvailabilityTemplateForDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        return enterpriseServiceSlotTemplateService.getAvailabilityTemplateForDayOfWeek(enterpiseServiceId, dayOfWeek)
                .stream()
                .map(NonCustomAppointmentsEnterpriseServiceSlotTemplate::from)
                .collect(Collectors.toList());
    }

}
