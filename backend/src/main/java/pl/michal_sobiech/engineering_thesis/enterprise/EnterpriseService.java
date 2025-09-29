package pl.michal_sobiech.engineering_thesis.enterprise;

import org.SwaggerCodeGenExample.model.CreateEnterpriseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    @Transactional
    public Enterprise createEnterprise(
            long userId,
            CreateEnterpriseRequest createEnterpriseRequest) {

        final Enterprise enterprise = Enterprise.builder()
                .entrepreneurUserId(userId)
                .name(createEnterpriseRequest.getEnterpriseName())
                .description(createEnterpriseRequest.getDescription())
                .location(createEnterpriseRequest.getLocation())
                .build();
        return enterpriseRepository.save(enterprise);
    }

    @Transactional
    public Enterprise getEnterprise(long enterpriseId) {
        return enterpriseRepository.getReferenceById(enterpriseId);
    }

}
