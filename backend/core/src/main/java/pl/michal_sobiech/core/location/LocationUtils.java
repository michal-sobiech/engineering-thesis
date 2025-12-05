package pl.michal_sobiech.core.location;

import org.geotools.referencing.GeodeticCalculator;

public class LocationUtils {

    public static double calcDistanceInKm(Position pos1, Position pos2) {
        GeodeticCalculator calculator = new GeodeticCalculator();
        calculator.setStartingGeographicPoint(pos1.longitude(), pos1.latitude());
        calculator.setDestinationGeographicPoint(pos2.longitude(), pos2.latitude());
        double distanceMeters = calculator.getOrthodromicDistance();
        return distanceMeters / 1000;
    }

}