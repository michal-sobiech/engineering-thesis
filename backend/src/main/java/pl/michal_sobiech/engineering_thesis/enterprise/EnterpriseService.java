package pl.michal_sobiech.engineering_thesis.enterprise;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise.controller.CreateEnterpriseCommand;
import pl.michal_sobiech.engineering_thesis.utils.MultipartFileUtils;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    @Transactional
    public Enterprise createEnterprise(
            long ownerUserId,
            CreateEnterpriseCommand enterpriseCommand) {

        var builder = Enterprise.builder()
                .ownerUserId(ownerUserId)
                .name(enterpriseCommand.name())
                .description(enterpriseCommand.description())
                .address(enterpriseCommand.location().getAddress())
                .longitude(enterpriseCommand.location().getLongitude())
                .latitude(enterpriseCommand.location().getLatitude());

        enterpriseCommand.logoFile().ifPresent(file -> {
            builder.logoFileName(file.getName());
            builder.logoFileBytes(MultipartFileUtils.getBytes(file));
        });

        enterpriseCommand.backgroundPhotoFile().ifPresent(file -> {
            builder.backgroundPhotoFileName(file.getName());
            builder.backgroundPhotoFileBytes(MultipartFileUtils.getBytes(file));
        });

        Enterprise enterprise = builder.build();
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

        request.location().ifPresent(location -> {
            enterprise.setAddress(location.getAddress());
            enterprise.setLongitude(location.getLongitude());
            enterprise.setLatitude(location.getLatitude());
        });
    }

}
