package app;

import app.helper.GPSCoordinate;
import gtfs.entities.*;
import gtfs.services.GTFSReader;
import gtfs.services.Router;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by robertomariano on 05/07/17.
 */
public class Main {
    static Map<String,Stop> stops;
    static Map<String,Route> routes;
    static Map<String,Shape> shapes;
    static Map<String,Service> services;
    static Map<String,Trip> trips;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        GPSCoordinate origin = null;
        GPSCoordinate destiny = null;

        Main.readGtfs();

//        System.out.println("Informe as coordenadas de origem (lat, long):");
//        double latOrigem = scanner.nextDouble();
//        double longOrigem = scanner.nextDouble();

//        origin = new GPSCoordinate(scanner.nextDouble(), scanner.nextDouble());

//        System.out.println("Informe as coordenadas de destino (lat, long):");
//        destiny = new GPSCoordinate(scanner.nextDouble(), scanner.nextDouble());

        origin = new GPSCoordinate(-30.026957, -51.181414);
        destiny = new GPSCoordinate(-30.01002395,-51.1888057);


        Router pathfinder = new Router(
            origin,
            destiny,
            Main.stops
        );

        pathfinder.route();
    }

    public static void readGtfs() throws FileNotFoundException {
        System.out.println("Reading stops.");
        Main.stops = GTFSReader.loadStops("data/stops.txt");
        System.out.println("Reading routes.");
        Main.routes = GTFSReader.loadRoutes("data/routes.txt");
        System.out.println("Reading shapes.");
        Main.shapes = GTFSReader.loadShapes("data/shapes.txt");
        System.out.println("Reading calendar.");
        Main.services = GTFSReader.loadServices("data/calendar.txt");
        System.out.println("Reading trips.");
        Main.trips = GTFSReader.loadTrips("data/trips.txt", Main.routes, Main.services, Main.shapes);
        System.out.println("Reading stop times.");
        long s = System.currentTimeMillis();
        GTFSReader.loadStopTimes("data/stop_times.txt", Main.trips, Main.stops);
        long e = System.currentTimeMillis();
        System.out.println("\nTime: " + ((e-s)/1000.0));
    }
}
