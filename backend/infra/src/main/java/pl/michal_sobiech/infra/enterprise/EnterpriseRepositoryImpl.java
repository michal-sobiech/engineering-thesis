package pl.michal_sobiech.infra.enterprise;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise.EnterpriseEntity;
import pl.michal_sobiech.core.enterprise.EnterpriseRepository;

@Component
@RequiredArgsConstructor
public class EnterpriseRepositoryImpl implements EnterpriseRepository {

    private final SpringEnterpriseRepository springEnterpriseRepository;

    @Override
    public EnterpriseEntity save(EnterpriseEntity record) {
        return springEnterpriseRepository.save(record);
    }

    @Override
    public Optional<EnterpriseEntity> findById(long enterpriseId) {
        return springEnterpriseRepository.findById(enterpriseId);
    }

    @Override
    public List<EnterpriseEntity> findAll() {
        return springEnterpriseRepository.findAll();
    }

    @Override
    public List<EnterpriseEntity> findAllByOwnerUserId(long ownerUserId) {
        return springEnterpriseRepository.findAllByOwnerUserId(ownerUserId);
    }

    @Override
    public List<EnterpriseEntity> findByNameContaining(String substring) {
        return springEnterpriseRepository.findByNameContaining(substring);
    }

}
