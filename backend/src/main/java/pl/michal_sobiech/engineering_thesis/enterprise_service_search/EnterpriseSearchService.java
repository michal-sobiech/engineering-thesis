package pl.michal_sobiech.engineering_thesis.enterprise_service_search;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegoryConverter;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotRepository;

@Service
@RequiredArgsConstructor
public class EnterpriseSearchService {

    private final EnterpriseService enterpriseService;
    private final EnterpriseServiceService enterpriseServiceService;
    private final EnterpriseServiceSlotRepository enterpriseServiceSlotRepository;
    private final EnterpriseServiceCathegoryConverter enterpriseServiceCathegoryConverter;

    public List<NoCustomAppointmentsSlot> searchNoCustomAppointmentsSlots(
            Optional<String> serviceName,
            Optional<String> enterpriseName,
            Optional<OffsetDateTime> startDate,
            Optional<OffsetDateTime> endDate,
            Optional<BigDecimal> preferredLongitude,
            Optional<BigDecimal> preferredLatitude,
            EnterpriseServiceCathegory cathegory) {
        var records = enterpriseServiceSlotRepository.filterNoCustomAppointmentsServiceSlots(
            serviceName.orElse(null),
            enterpriseName.orElse(null),
            startDate.orElse(null),
            endDate.orElse(null),
            preferredLongitude.orElse(null),
            preferredLatitude.orElse(null),
            enterpriseServiceCathegoryConverter.convertToDatabaseColumn(cathegory),
        );
    }

}
