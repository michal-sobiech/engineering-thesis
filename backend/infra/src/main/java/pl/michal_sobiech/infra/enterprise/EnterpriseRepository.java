package pl.michal_sobiech.infra.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.michal_sobiech.core.enterprise.EnterpriseEntity;

public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {

    public List<EnterpriseEntity> findAllByOwnerUserId(long ownerUserId);

    public List<EnterpriseEntity> findByNameContaining(String substring);
}
