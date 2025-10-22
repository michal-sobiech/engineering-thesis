package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseServiceRepository extends JpaRepository<EnterpriseServiceEntity, Long> {
    public List<EnterpriseServiceEntity> findByEnterpriseId(long enterpriseId);
}
