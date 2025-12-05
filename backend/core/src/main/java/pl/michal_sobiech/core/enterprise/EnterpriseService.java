package pl.michal_sobiech.core.enterprise;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.model.File;

@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    public List<EnterpriseEntity> findAllByOwnerUserId(long ownerUserId) {
        return enterpriseRepository.findAllByOwnerUserId(ownerUserId);
    }

    @Transactional
    public Enterprise createEnterprise(
            long ownerUserId,
            String name,
            String description,
            String address,
            double longitude,
            double latitude,
            Optional<File> logoFile,
            Optional<File> backgroundPhotoFile) {

        var builder = EnterpriseEntity.builder()
                .ownerUserId(ownerUserId)
                .name(name)
                .description(description)
                .address(address)
                .longitude(longitude)
                .latitude(latitude);

        logoFile.ifPresent(file -> {
            builder.logoFileName(file.name());
            builder.logoFileBytes(file.bytes());
        });

        backgroundPhotoFile.ifPresent(file -> {
            builder.backgroundPhotoFileName(file.name());
            builder.backgroundPhotoFileBytes(file.bytes());
        });

        EnterpriseEntity enterprise = builder.build();
        enterprise = enterpriseRepository.save(enterprise);

        return Enterprise.fromEntity(enterprise);
    }

    public Optional<Enterprise> getEnterprise(long enterpriseId) {
        return enterpriseRepository.findById(enterpriseId).map(Enterprise::fromEntity);
    }

    // @Transactional
    // public void patchEnterprise(PatchEnterpriseRequestDto request) {

    // // TODO

    // // EnterpriseEntity enterprise =
    // // enterpriseRepository.findById(request.enterpriseId()).orElseThrow();

    // // request.name().ifPresent(name -> enterprise.setName(name));
    // // request.description().ifPresent(description ->
    // // enterprise.setDescription(description));

    // // request.location().ifPresent(location -> {
    // // enterprise.setAddress(location.getAddress());
    // // enterprise.setLongitude(location.getLongitude());
    // // enterprise.setLatitude(location.getLatitude());
    // // });
    // }

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
