package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseServiceRepository extends JpaRepository<EnterpriseServiceEntity, Long> {

    public List<EnterpriseServiceEntity> findByEnterpriseId(long enterpriseId);

    public Optional<ZoneId> findTimeZoneByEnterpriseServiceId(long enterpriseServiceId);

}
