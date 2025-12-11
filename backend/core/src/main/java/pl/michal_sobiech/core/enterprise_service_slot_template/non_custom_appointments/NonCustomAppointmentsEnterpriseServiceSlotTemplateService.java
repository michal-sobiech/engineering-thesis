package pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;

@RequiredArgsConstructor
public class NonCustomAppointmentsEnterpriseServiceSlotTemplateService {

    private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository;

    @Transactional
    public List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> saveMany(long enterpriseServiceId,
            List<CreateSlotTemplateCommand> commands) {
        return commands.stream().map(command -> save(enterpriseServiceId, command))
                .collect(Collectors.toList());
    }

    @Transactional
    public NonCustomAppointmentsEnterpriseServiceSlotTemplate save(long enterpriseServiceId,
            CreateSlotTemplateCommand command) {
        EnterpriseServiceSlotTemplateEntity slot = EnterpriseServiceSlotTemplateEntity.builder()
                .enterpriseServiceId(enterpriseServiceId)
                .dayOfWeek(command.dayOfWeek())
                .startTime(command.startTime())
                .endTime(command.endTime())
                .maxOccupancy(command.maxOccupancy())
                .build();
        slot = enterpriseServiceSlotTemplateRepository.save(slot);
        return NonCustomAppointmentsEnterpriseServiceSlotTemplate.from(slot);
    }

    public List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> getAllByEnterpriseServiceIdAndDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotTemplateEntity> records = enterpriseServiceSlotTemplateRepository
                .findAllByEnterpriseServiceIdAndDayOfWeek(enterpiseServiceId,
                        dayOfWeek);
        return records.stream().map(record -> NonCustomAppointmentsEnterpriseServiceSlotTemplate.from(record))
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
