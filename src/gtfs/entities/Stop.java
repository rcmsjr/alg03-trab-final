package gtfs.entities;

import app.helper.GPSCoordinate;

import java.util.*;

/**
 * Created by robertomariano on 05/07/17.
 */
public class Stop extends EntityBase {
    private String name;
    private GPSCoordinate coord;
    private List<Trip> trips;

    public Stop(String id, String name, double latitude, double longitude) {
        super(id);
        this.name = name;
        this.coord = new GPSCoordinate(latitude, longitude);
        this.trips = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public GPSCoordinate getGPSCoordinate() {
        return coord;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Returns the same representation as used in the file stops.txt.
     */
    @Override
    public String toString() {
        return String.format(Locale.US,"%s,,%s,,%.8g,%.8g",
                getId(),name,coord.latitude,coord.longitude);
    }

    public void addTrip(Trip lastTrip) {
        if (! this.trips.contains(lastTrip))
            this.trips.add(lastTrip);
    }

    public double distance(GPSCoordinate place) {
        return this.coord.distance(place);
    }
}
