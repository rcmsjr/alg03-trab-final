package gtfs.services;

import app.helper.GPSCoordinate;
import gtfs.entities.Route;
import gtfs.entities.Stop;
import gtfs.entities.Trip;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by thiago on 08/07/17.
 */
public class Pathfinder {
    private Map<String, Stop> stops;
    private GPSCoordinate destiny;
    private double minDisToDestiny = 999999999;
    private ArrayList<Step> steps = new ArrayList<>();

    public class Step {
        private Route route;
        private Stop stop;

        public Step(Route route, Stop stop) {
            this.route = route;
            this.stop = stop;
        }
    }

    public Pathfinder(GPSCoordinate destiny, Map<String, Stop> stops) {
        this.destiny = destiny;
        this.stops = stops;
    }

    public void findPath(Stop stopA) {
        Stop nearestStop = null;
        Route routeUsed = null;

        double minDis = 999999999;

        for (Trip trip : stopA.getTrips()) {
            Stop stopB = trip.nearestStop(destiny);
            double dis = destiny.distance(stopB.getGPSCoordinate());

            if (dis < minDis) {
                nearestStop = stopB;
                routeUsed = trip.getRoute();
                minDis = dis;
            }
        }

        if (minDis < minDisToDestiny) {
            steps.add(new Step(routeUsed, nearestStop));
            minDisToDestiny = minDis;
            findPath(nearestStop);
        } else {
            System.out.println("fim");
        }
    }
}
