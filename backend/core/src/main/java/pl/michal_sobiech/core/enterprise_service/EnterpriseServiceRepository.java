package pl.michal_sobiech.core.enterprise_service;

import java.util.List;
import java.util.Optional;

import pl.michal_sobiech.core.enteprise_service_search.EnterpriseServiceSearchResultRow;

public interface EnterpriseServiceRepository {

    public EnterpriseServiceEntity save(EnterpriseServiceEntity record);

    public Optional<EnterpriseServiceEntity> findById(long enterpriseServiceId);

    public List<EnterpriseServiceEntity> findByEnterpriseId(long enterpriseId);

    public List<EnterpriseServiceSearchResultRow> search(
            boolean takesCustomAppointments,
            String serviceName,
            String enterpriseName,
            EnterpriseServiceCathegory cathegory,
            double customerLatitude,
            double customerLongitude,
            double maxDistance);

    public Optional<Boolean> findTakesCustomAppointmentsByEnterpriseServiceId(
            long enterpriseServiceId);

    public List<EnterpriseServiceEntity> findNonCustomEnterpriseServicesByEnterpriseId(
            long enterpriseId);

    public List<EnterpriseServiceEntity> findCustomEnterpriseServicesByEnterpriseId(
            long enterpriseId);

}
