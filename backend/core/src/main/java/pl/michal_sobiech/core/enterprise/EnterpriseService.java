package pl.michal_sobiech.core.enterprise;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise.controller.CreateEnterpriseCommand;
import pl.michal_sobiech.engineering_thesis.utils.MultipartFileUtils;

@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    public List<EnterpriseEntity> findAllByOwnerUserId(long ownerUserId) {
        return enterpriseRepository.findAllByOwnerUserId(ownerUserId);
    }

    @Transactional
    public Enterprise createEnterprise(
            long ownerUserId,
            CreateEnterpriseCommand enterpriseCommand) {

        var builder = EnterpriseEntity.builder()
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

        EnterpriseEntity enterprise = builder.build();
        enterprise = enterpriseRepository.save(enterprise);

        return Enterprise.fromEntity(enterprise);
    }

    public Optional<Enterprise> getEnterprise(long enterpriseId) {
        return enterpriseRepository.findById(enterpriseId).map(Enterprise::fromEntity);
    }

    @Transactional
    public void patchEnterprise(PatchEnterpriseRequestDto request) {

        // TODO

        EnterpriseEntity enterprise = enterpriseRepository.findById(request.enterpriseId()).orElseThrow();

        request.name().ifPresent(name -> enterprise.setName(name));
        request.description().ifPresent(description -> enterprise.setDescription(description));

        request.location().ifPresent(location -> {
            enterprise.setAddress(location.getAddress());
            enterprise.setLongitude(location.getLongitude());
            enterprise.setLatitude(location.getLatitude());
        });
    }

    public List<Enterprise> searchEnterprisesWithSubstringInName(String substring) {
        return enterpriseRepository.findByNameContaining(substring)
                .stream()
                .map(Enterprise::fromEntity)
                .collect(Collectors.toList());
    }

    public List<Enterprise> getAll() {
        return enterpriseRepository.findAll()
                .stream()
                .map(Enterprise::fromEntity)
                .collect(Collectors.toList());
    }

}
