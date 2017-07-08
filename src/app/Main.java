package app;

import app.helper.GPSCoordinate;
import gtfs.entities.*;
import gtfs.services.GTFSReader;

import java.io.FileNotFoundException;
import java.util.Iterator;
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
    static PeopleRoute peopleRoute = new PeopleRoute();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        Main.readGtfs();

//        System.out.println("Informe as coordenadas de origem (lat, long):");
//        double latOrigem = scanner.nextDouble();
//        double longOrigem = scanner.nextDouble();

        Main.peopleRoute.setOrigin(new GPSCoordinate(-30.061908, -51.188538));

//        System.out.println("Informe as coordenadas de destino (lat, long):");
//        double latDest = scanner.nextDouble();
//        double longDest = scanner.nextDouble();

        Main.peopleRoute.setDestiny(new GPSCoordinate(-30.0629887,-51.1684136));

        Stop origem = null;
        Stop destino = null;
        double menorDistanciaOrig = 999999999;
        double menorDistanciaDest = 999999999;

        for (Stop s : Main.stops.values()) {
            double disOrig = s.getGPSCoordinate().distance(Main.peopleRoute.getOrigin());
            double disDest = s.getGPSCoordinate().distance(Main.peopleRoute.getDestiny());

            if (disOrig < menorDistanciaOrig) {
                origem = s;
                menorDistanciaOrig = disOrig;
            }

            if (disDest < menorDistanciaDest) {
                destino = s;
                menorDistanciaDest = disDest;
            }
        }

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
