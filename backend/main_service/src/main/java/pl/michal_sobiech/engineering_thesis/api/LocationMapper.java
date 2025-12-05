package pl.michal_sobiech.engineering_thesis.api;

import pl.michal_sobiech.core.location.Location;

public class LocationMapper {

    public static Location fromSwagger(org.SwaggerCodeGenExample.model.Location swaggerLocation) {
        return new Location(
                swaggerLocation.getAddress(),
                swaggerLocation.getLongitude(),
                swaggerLocation.getLatitude());
    }

    public static org.SwaggerCodeGenExample.model.Location fromDomain(Location domainLocation) {
        return new org.SwaggerCodeGenExample.model.Location(
                domainLocation.address(),
                domainLocation.longitude(),
                domainLocation.latitude());
    }

}
