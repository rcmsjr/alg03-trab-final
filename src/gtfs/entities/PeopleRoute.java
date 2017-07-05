package gtfs.entities;

import app.helper.GPSCoordinate;

/**
 * Created by robertomariano on 05/07/17.
 */
public class PeopleRoute {
    private GPSCoordinate destiny;
    private GPSCoordinate origin;

    public GPSCoordinate getDestiny() {
        return destiny;
    }

    public void setDestiny(GPSCoordinate destiny) {
        this.destiny = destiny;
    }

    public GPSCoordinate getOrigin() {
        return origin;
    }

    public void setOrigin(GPSCoordinate origin) {
        this.origin = origin;
    }
}
