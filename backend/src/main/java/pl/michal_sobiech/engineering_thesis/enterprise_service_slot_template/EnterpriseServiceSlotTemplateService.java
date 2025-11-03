package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnterpriseServiceSlotTemplateService {

    private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository;

    @Transactional
    public List<EnterpriseServiceSlotTemplateEntity> saveMany(long enterpriseServiceId,
            List<CreateEnterpriseServiceSlotTemplateCommand> commands) {
        return commands.stream().map(command -> save(enterpriseServiceId, command)).collect(Collectors.toList());
    }

    @Transactional
    public EnterpriseServiceSlotTemplateEntity save(long enterpriseServiceId,
            CreateEnterpriseServiceSlotTemplateCommand command) {
        EnterpriseServiceSlotTemplateEntity slot = EnterpriseServiceSlotTemplateEntity.builder()
                .enterpriseServiceId(enterpriseServiceId)
                .dayOfWeek(command.dayOfWeek())
                .startTime(command.startTime())
                .endTime(command.endTime())
                .build();
        return enterpriseServiceSlotTemplateRepository.save(slot);
    }

    public List<EnterpriseServiceSlotTemplateEntity> findAllByEnterpriseServiceId(long enterpriseServiceId) {
        return enterpriseServiceSlotTemplateRepository.findAllByEnterpriseServiceId(enterpriseServiceId);
    }

    public List<EnterpriseServiceSlotTemplateEntity> getAvailabilityTemplateForDayOfWeek(long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        return enterpriseServiceSlotTemplateRepository.findAllByEnterpriseServiceIdAndDayOfWeek(enterpiseServiceId,
                dayOfWeek);
    }

}
