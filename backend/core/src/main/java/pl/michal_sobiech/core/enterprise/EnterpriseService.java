package pl.michal_sobiech.core.enterprise;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.location.Location;
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

    @Transactional
    public void patchEnterprise(
            long enterpriseId,
            Optional<String> name,
            Optional<String> description,
            Optional<Location> location,
            Optional<File> logo,
            Optional<File> backgroundPhoto) {
        EnterpriseEntity enterprise = enterpriseRepository.findById(enterpriseId).orElseThrow();

        name.ifPresent(value -> enterprise.setName(value));
        description.ifPresent(value -> enterprise.setDescription(value));

        location.ifPresent(value -> {
            enterprise.setAddress(value.address());
            enterprise.setLongitude(value.longitude());
            enterprise.setLatitude(value.latitude());
        });

        logo.ifPresent(value -> {
            enterprise.setLogoFileName(value.name());
            enterprise.setLogoFileBytes(value.bytes());
        });

        backgroundPhoto.ifPresent(value -> {
            enterprise.setBackgroundPhotoFileName(value.name());
            enterprise.setBackgroundPhotoFileBytes(value.bytes());
        });

        enterpriseRepository.save(enterprise);
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

    public Optional<Enterprise> getById(long enterpriseId) {
        return enterpriseRepository.findById(enterpriseId).map(Enterprise::fromEntity);
    }

}
