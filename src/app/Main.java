package app;

import app.helper.GPSCoordinate;
import gtfs.entities.*;
import gtfs.services.GTFSReader;
import gtfs.services.Router;

import java.io.FileNotFoundException;
import java.util.List;
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
        destiny = new GPSCoordinate(-30.0352924,-51.2264149);


        Router router = new Router(
            origin,
            destiny,
            Main.stops
        );

        List<Router.Step> route = router.route();

        Stop lastStop = null;

        for(Router.Step step : route) {
            if(step.getStopOrigin() != lastStop) {
                System.out.println("Dirija-se até a parada " + step.getStopOrigin().getName() + " com as seguintes coordenadas: latitude " + step.getStopOrigin().getGPSCoordinate().latitude + " e longitude " + step.getStopOrigin().getGPSCoordinate().longitude);
            }

            System.out.println("Aguarde pelo onibus " + step.getRoute().getShortName() + " - " + step.getRoute().getLongName());
            System.out.println("Viaje até a parada " + step.getStop().getName() + " com as seguintes coordenadas: latitude " + step.getStop().getGPSCoordinate().latitude + " e longitude " + step.getStop().getGPSCoordinate().longitude);
            lastStop = step.getStop();
        }
    }

    public static void readGtfs() throws FileNotFoundException {
        System.out.println("Aguarde, carregando...");
//        System.out.println("Reading stops.");
        Main.stops = GTFSReader.loadStops("data/stops.txt");
//        System.out.println("Reading routes.");
        Main.routes = GTFSReader.loadRoutes("data/routes.txt");
//        System.out.println("Reading shapes.");
        Main.shapes = GTFSReader.loadShapes("data/shapes.txt");
//        System.out.println("Reading calendar.");
        Main.services = GTFSReader.loadServices("data/calendar.txt");
//        System.out.println("Reading trips.");
        Main.trips = GTFSReader.loadTrips("data/trips.txt", Main.routes, Main.services, Main.shapes);
//        System.out.println("Reading stop times.");
//        long s = System.currentTimeMillis();
        GTFSReader.loadStopTimes("data/stop_times.txt", Main.trips, Main.stops);
//        long e = System.currentTimeMillis();
//        System.out.println("\nTime: " + ((e-s)/1000.0));
    }
}
