package pl.michal_sobiech.core.location;

public class Location {

    private final String address;

    private final Position position;

    public Location(String address, double longitude, double latitude) {
        this.address = address;
        this.position = new Position(longitude, latitude);
    }

    public String address() {
        return address;
    }

    public double longitude() {
        return position.longitude();
    }

    public double latitude() {
        return position.latitude();
    }

}
