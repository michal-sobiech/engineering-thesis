package pl.michal_sobiech.infra.enterprise_service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enteprise_service_search.EnterpriseServiceSearchResultRow;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceRepository;

@Component
@RequiredArgsConstructor
public class EnterpriseServiceRepositoryImpl implements EnterpriseServiceRepository {

    private final SpringEnterpriseServiceRepository springEnterpriseServiceRepository;

    @Override
    public EnterpriseServiceEntity save(EnterpriseServiceEntity record) {
        return springEnterpriseServiceRepository.save(record);
    }

    @Override
    public Optional<EnterpriseServiceEntity> findById(long enterpriseServiceId) {
        return springEnterpriseServiceRepository.findById(enterpriseServiceId);
    }

    @Override
    public List<EnterpriseServiceEntity> findByEnterpriseId(long enterpriseId) {
        return springEnterpriseServiceRepository.findByEnterpriseId(enterpriseId);
    }

    @Override
    public List<EnterpriseServiceSearchResultRow> search(
            boolean takesCustomAppointments,
            String serviceName,
            String enterpriseName,
            EnterpriseServiceCathegory cathegory,
            double customerLatitude,
            double customerLongitude,
            double maxDistance) {
        return springEnterpriseServiceRepository.search(takesCustomAppointments, serviceName, enterpriseName, cathegory,
                customerLatitude, customerLongitude, maxDistance);
    }

    @Override
    public Optional<Boolean> findTakesCustomAppointmentsByEnterpriseServiceId(
            long enterpriseServiceId) {
        return springEnterpriseServiceRepository.findTakesCustomAppointmentsByEnterpriseServiceId(enterpriseServiceId);
    }

    @Override
    public List<EnterpriseServiceEntity> findNonCustomEnterpriseServicesByEnterpriseId(
            long enterpriseId) {
        return springEnterpriseServiceRepository.findNonCustomEnterpriseServicesByEnterpriseId(enterpriseId);
    }

    @Override
    public List<EnterpriseServiceEntity> findCustomEnterpriseServicesByEnterpriseId(
            long enterpriseId) {
        return springEnterpriseServiceRepository.findCustomEnterpriseServicesByEnterpriseId(enterpriseId);
    }

}
