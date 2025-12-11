package pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;

@RequiredArgsConstructor
public class NonCustomSlotTemplateService {

    private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository;

    @Transactional
    public List<NonCustomSlotTemplate> saveMany(long enterpriseServiceId,
            List<CreateSlotTemplateCommand> commands) {
        return commands.stream().map(command -> save(enterpriseServiceId, command))
                .collect(Collectors.toList());
    }

    @Transactional
    public NonCustomSlotTemplate save(long enterpriseServiceId,
            CreateSlotTemplateCommand command) {
        EnterpriseServiceSlotTemplateEntity slot = EnterpriseServiceSlotTemplateEntity.builder()
                .enterpriseServiceId(enterpriseServiceId)
                .dayOfWeek(command.dayOfWeek())
                .startTime(command.startTime())
                .endTime(command.endTime())
                .maxOccupancy(command.maxOccupancy())
                .build();
        slot = enterpriseServiceSlotTemplateRepository.save(slot);
        return NonCustomSlotTemplate.from(slot);
    }

    public List<NonCustomSlotTemplate> getAllByEnterpriseServiceIdAndDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotTemplateEntity> records = enterpriseServiceSlotTemplateRepository
                .findAllByEnterpriseServiceIdAndDayOfWeek(enterpiseServiceId,
                        dayOfWeek);
        return records.stream().map(record -> NonCustomSlotTemplate.from(record))
                .collect(Collectors.toList());
    }

    public List<NonCustomSlotTemplate> getAllInWeekByEnterpriseServiceId(
            long enterpiseServiceId) {
        return Arrays.stream(DayOfWeek.values())
                .map(day -> getAllByEnterpriseServiceIdAndDayOfWeek(enterpiseServiceId, day))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Transactional
    public void overwriteEnterpriseServiceSlotTemplates(
            long enterpriseServiceId,
            List<CreateSlotTemplateCommand> commands) {
        deleteEnterpriseServiceSlotTemplates(enterpriseServiceId);
        saveMany(enterpriseServiceId, commands);
    }

    @Transactional
    public void deleteEnterpriseServiceSlotTemplates(long enterpiseServiceId) {
        enterpriseServiceSlotTemplateRepository.deleteAllByEnterpriseServiceId(enterpiseServiceId);
    }

}
