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
public class Router {
    private Map<String, Stop> stops;
    private GPSCoordinate start;
    private GPSCoordinate end;

    public class Step {
        private Route route;
        private Stop stop;

        public Step(Route route, Stop stop) {
            this.route = route;
            this.stop = stop;
        }
    }

    public Router(GPSCoordinate start, GPSCoordinate end, Map<String, Stop> stops) {
        this.start = start;
        this.end = end;
        this.stops = stops;
    }

    public void route() {
        ArrayList<Step> steps = new ArrayList<>();
        findPath(nearStop(start), steps);
    }

    public void findPath(Stop stop, ArrayList<Step> steps) {
        findPath(stop, steps, 999999999);
    }

    public void findPath(Stop stopA, ArrayList<Step> steps, double minDisToTarget) {
        Stop nearestStop = null;
        Route routeUsed = null;

        double minDis = 999999999;

        for (Trip trip : stopA.getTrips()) {
            Stop stopB = trip.nearestStop(end);
            double dis = end.distance(stopB.getGPSCoordinate());

            if (dis < minDis) {
                nearestStop = stopB;
                routeUsed = trip.getRoute();
                minDis = dis;
            }
        }

        if (minDis < minDisToTarget) {
            steps.add(new Step(routeUsed, nearestStop));
            findPath(nearestStop, steps, minDis);
        } else {
            Stop closerStop = closerStop(nearestStop, minDisToTarget);

            if (null != closerStop) {
                findPath(closerStop, steps);
            }
        }
    }

    private Stop closerStop(Stop nearestStop, double minDisToTarget) {
        Stop closerStop = null;
        for (Stop stop : stops.values()) {
            double distance = stop.distance(nearestStop);

            distance += stop.distance(end);

            if (distance < minDisToTarget) {
                minDisToTarget = distance;
                closerStop = stop;
            }
        }

        return closerStop;
    }

    /**
     * Find near stop to some place
     * @param place
     * @return
     */
    public Stop nearStop(GPSCoordinate place) {
        Stop stop = null;
        double minDis = 999999999;

        for (Stop s : stops.values()) {
            double dis = s.distance(place);

            if (dis < minDis) {
                stop = s;
                minDis = dis;
            }
        }

        return stop;
    }
}
