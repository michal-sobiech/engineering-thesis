package pl.michal_sobiech.engineering_thesis.enterprise;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.photo.Photo;
import pl.michal_sobiech.engineering_thesis.photo.PhotoService;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final PhotoService photoService;

    @Transactional
    public Enterprise createEnterprise(
            long entrepreneurId,
            String name,
            String description,
            String location,
            Optional<MultipartFile> logoFile,
            Optional<MultipartFile> backgroundPhotoFile) {

        var builder = Enterprise.builder()
                .entrepreneurId(entrepreneurId)
                .name(name)
                .description(description)
                .location(location);

        if (logoFile.isPresent()) {
            MultipartFile file = logoFile.get();
            Photo logo = photoService.createPhoto(file);
            builder.logoPhotoId(logo.getPhotoId());
        }

        if (backgroundPhotoFile.isPresent()) {
            MultipartFile file = backgroundPhotoFile.get();
            Photo backgroundPhoto = photoService.createPhoto(file);
            builder.backgroundPhotoId(backgroundPhoto.getPhotoId());
        }

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
        request.location().ifPresent(location -> enterprise.setLocation(location));

        request.logoFile().ifPresent(file -> {
            Photo photo = photoService.createPhoto(file);
            enterprise.setLogoPhotoId(photo.getPhotoId());
        });

        request.backgroundPhotoFile().ifPresent(file -> {
            Photo photo = photoService.createPhoto(file);
            enterprise.setBackgroundPhotoId(photo.getPhotoId());
        });

    }

}
