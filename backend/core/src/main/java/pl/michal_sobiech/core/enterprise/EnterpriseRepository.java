package pl.michal_sobiech.core.enterprise;

import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository {

    public EnterpriseEntity save(EnterpriseEntity record);

    public Optional<EnterpriseEntity> findById(long enterpriseId);

    public List<EnterpriseEntity> findAll();

    public List<EnterpriseEntity> findAllByOwnerUserId(long ownerUserId);

    public List<EnterpriseEntity> findByNameContaining(String substring);

}