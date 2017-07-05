package app;

import app.helper.GPSCoordinate;
import gtfs.entities.*;
import gtfs.services.GTFSReader;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by robertomariano on 05/07/17.
 */
public class Start {
    static Map<String,Stop> stops;
    static Map<String,Route> routes;
    static Map<String,Shape> shapes;
    static Map<String,Service> services;
    static Map<String,Trip> trips;
    static PeopleRoute peopleRoute = new PeopleRoute();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        Start.readGtfs();

        System.out.println("Informe as coordenadas de origem (lat, long):");
        double latOrigem = scanner.nextDouble();
        double longOrigem = scanner.nextDouble();

        Start.peopleRoute.setOrigin(new GPSCoordinate(latOrigem, longOrigem));

        System.out.println("Informe as coordenadas de destino (lat, long):");
        double latDest = scanner.nextDouble();
        double longDest = scanner.nextDouble();

        Start.peopleRoute.setDestiny(new GPSCoordinate(latDest, longDest));
    }

    public static void readGtfs() throws FileNotFoundException {
        System.out.println("Reading stops.");
        Start.stops = GTFSReader.loadStops("src/resources/data/stops.txt");
        System.out.println("Reading routes.");
        Start.routes = GTFSReader.loadRoutes("src/resources/data/routes.txt");
        System.out.println("Reading shapes.");
        Start.shapes = GTFSReader.loadShapes("src/resources/data/shapes.txt");
        System.out.println("Reading calendar.");
        Start.services = GTFSReader.loadServices("src/resources/data/calendar.txt");
        System.out.println("Reading trips.");
        Start.trips = GTFSReader.loadTrips("src/resources/data/trips.txt",Start.routes,Start.services,Start.shapes);
        System.out.println("Reading stop times.");
        long s = System.currentTimeMillis();
        GTFSReader.loadStopTimes("src/resources/data/stop_times.txt", Start.trips, Start.stops);
        long e = System.currentTimeMillis();
        System.out.println("\nTime: " + ((e-s)/1000.0));
    }
}
