package pl.michal_sobiech.engineering_thesis.enterprise_service_slot;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnterpriseServiceSlotService {

    private final EnterpriseServiceSlotRepository enterpriseServiceSlotRepository;

    @Transactional
    public List<EnterpriseServiceSlot> saveMany(long enterpriseServiceId,
            List<CreateEnterpriseServiceSlotCommand> commands) {
        return commands.stream().map(command -> save(enterpriseServiceId, command)).toList();
    }

    @Transactional
    public EnterpriseServiceSlot save(long enterpriseServiceId, CreateEnterpriseServiceSlotCommand command) {
        EnterpriseServiceSlot slot = EnterpriseServiceSlot.builder()
                .enterpriseServiceId(enterpriseServiceId)
                .dayOfWeek(command.dayOfWeek())
                .startTime(command.startTime())
                .endTime(command.endTime())
                .build();
        return enterpriseServiceSlotRepository.save(slot);
    }

}
