package pl.michal_sobiech.engineering_thesis.enterprise;

import java.util.List;
import java.util.Optional;

import org.SwaggerCodeGenExample.model.CreateEnterpriseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.photo.PhotoService;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final PhotoService photoService;

    @Transactional
    public Enterprise createEnterprise(
            long entrepreneurId,
            CreateEnterpriseRequest createEnterpriseRequest) {

        final Enterprise enterprise = Enterprise.builder()
                .entrepreneurId(entrepreneurId)
                .name(createEnterpriseRequest.getName())
                .description(createEnterpriseRequest.getDescription())
                .location(createEnterpriseRequest.getLocation())
                .build();
        return enterpriseRepository.save(enterprise);
    }

    @Transactional
    public Enterprise getEnterprise(long enterpriseId) {
        return enterpriseRepository.getReferenceById(enterpriseId);
    }

    @Transactional
    public Optional<Enterprise> findByEnterpriseId(long enterpriseId) {
        return enterpriseRepository.findById(enterpriseId);
    }

    // TODO remove unnecessary @Tranasctional annotations
    @Transactional
    public List<Enterprise> findAllByEntrepreneurId(long entrepreneurId) {
        return enterpriseRepository.findAllByEntrepreneurId(entrepreneurId);
    }

    @Transactional
    public void patchEnterprise(PatchEnterpriseRequestDto request) {

        Enterprise enterprise = enterpriseRepository.findById(request.enterpriseId()).orElseThrow();

        request.name().ifPresent(name -> enterprise.setName(name));
        request.description().ifPresent(description -> enterprise.setDescription(description));
        request.location().ifPresent(location -> enterprise.setLocation(location));
        request.logoFile().ifPresent(file -> photoService.createPhoto(file));
        request.backgroundPhotoFile().ifPresent(file -> photoService.createPhoto(file));

    }

}
