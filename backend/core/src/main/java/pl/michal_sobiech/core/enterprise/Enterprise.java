package pl.michal_sobiech.core.enterprise;

import java.util.Optional;

import pl.michal_sobiech.core.location.Location;
import pl.michal_sobiech.core.location.Position;
import pl.michal_sobiech.core.model.File;

public record Enterprise(

        long enterpriseId,

        long ownerUserId,

        String name,
        String description,

        Optional<Location> location,

        Optional<File> logo,
        Optional<File> backgroundPhoto,

        boolean suspendedByAdmin

) {

    public static Enterprise fromEntity(EnterpriseEntity entity) {
        boolean isLocationComplete = (entity.getAddress() != null
                && entity.getLongitude() != null
                && entity.getLatitude() != null);
        boolean isLocationEmpty = (entity.getAddress() == null
                && entity.getLongitude() == null
                && entity.getLatitude() == null);
        Optional<Location> location;
        if (isLocationComplete) {
            location = Optional.of(new Location(
                    entity.getAddress(),
                    new Position(
                            entity.getLongitude(),
                            entity.getLatitude())));
        } else if (isLocationEmpty) {
            location = Optional.empty();
        } else {
            throw new IllegalArgumentException();
        }

        boolean islogoComplete = entity.getLogoFileName() != null && entity.getLogoFileBytes() != null;
        boolean islogoEmpty = entity.getLogoFileName() == null && entity.getLogoFileBytes() == null;
        Optional<File> logo;
        if (islogoComplete) {
            logo = Optional.of(new File(
                    entity.getLogoFileName(),
                    entity.getLogoFileBytes()));
        } else if (islogoEmpty) {
            logo = Optional.empty();
        } else {
            throw new IllegalArgumentException();
        }

        boolean isBackgroundPhotoComplete = entity.getBackgroundPhotoFileName() != null
                && entity.getBackgroundPhotoFileBytes() != null;
        boolean isBackgroundPhotoEmpty = entity.getBackgroundPhotoFileName() == null
                && entity.getBackgroundPhotoFileBytes() == null;
        Optional<File> backgroundPhoto;
        if (isBackgroundPhotoComplete) {
            backgroundPhoto = Optional.of(new File(
                    entity.getBackgroundPhotoFileName(),
                    entity.getBackgroundPhotoFileBytes()));
        } else if (isBackgroundPhotoEmpty) {
            backgroundPhoto = Optional.empty();
        } else {
            throw new IllegalArgumentException();
        }

        return new Enterprise(
                entity.getEnterpriseId(),
                entity.getOwnerUserId(),
                entity.getName(),
                entity.getDescription(),
                location,
                logo,
                backgroundPhoto,
                entity.isSuspendedByAdmin());
    }

}