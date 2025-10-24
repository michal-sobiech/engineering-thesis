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
    public List<EnterpriseServiceSlotEntity> saveMany(long enterpriseServiceId,
            List<CreateEnterpriseServiceSlotCommand> commands) {
        return commands.stream().map(command -> save(enterpriseServiceId, command)).toList();
    }

    @Transactional
    public EnterpriseServiceSlotEntity save(long enterpriseServiceId, CreateEnterpriseServiceSlotCommand command) {
        EnterpriseServiceSlotEntity slot = EnterpriseServiceSlotEntity.builder()
                .enterpriseServiceId(enterpriseServiceId)
                .dayOfWeek(command.dayOfWeek())
                .startTime(command.startTime())
                .endTime(command.endTime())
                .build();
        return enterpriseServiceSlotRepository.save(slot);
    }

    public List<EnterpriseServiceSlotEntity> findAllByEnterpriseServiceId(long enterpriseServiceId) {
        return enterpriseServiceSlotRepository.findAllByEnterpriseServiceId(enterpriseServiceId);
    }

}
