package pl.michal_sobiech.engineering_thesis.location;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public Location save(org.SwaggerCodeGenExample.model.Location location) {
        var record = Location.builder().address(location.getAddress())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude()).build();
        return locationRepository.save(record);
    }

    public Optional<Location> findByLocationId(long locationId) {
        return locationRepository.findById(locationId);
    }

}
