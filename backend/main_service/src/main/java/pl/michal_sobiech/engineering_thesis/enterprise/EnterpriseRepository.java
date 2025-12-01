package pl.michal_sobiech.engineering_thesis.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {

    public List<EnterpriseEntity> findAllByOwnerUserId(long ownerUserId);

    public List<EnterpriseEntity> findByNameContaining(String substring);
}
