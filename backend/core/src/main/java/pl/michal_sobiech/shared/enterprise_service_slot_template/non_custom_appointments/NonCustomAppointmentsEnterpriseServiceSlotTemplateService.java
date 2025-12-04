package pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;

@Service
@RequiredArgsConstructor
public class NonCustomAppointmentsEnterpriseServiceSlotTemplateService {

        private final EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository;
        private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;

        @Transactional
        public List<NonCustomAppointmentsEnterpriseServiceSlotTemplate> saveMany(long enterpriseServiceId,
                        List<CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand> commands) {
                return commands.stream().map(command -> save(enterpriseServiceId, command))
                                .collect(Collectors.toList());
        }

        @Transactional
        public NonCustomAppointmentsEnterpriseServiceSlotTemplate save(long enterpriseServiceId,
                        CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand command) {
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

}
