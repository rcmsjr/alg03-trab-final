import smartcity.gtfs.*;
import smartcity.util.GPSCoordinate;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by thiago on 30/06/17.
 */
public class Rota {
    private GPSCoordinate destino;
    private GPSCoordinate origem;
    Map<String,Stop> stops;
    Map<String,Route> routes;
    Map<String,Shape> shapes;
    Map<String,Service> services;
    Map<String,Trip> trips;

    public void setOrigem(GPSCoordinate origem) {
        this.origem = origem;
    }

    public void setDestino(GPSCoordinate destino) {
        this.destino = destino;
    }

    public ArrayList<Route> calcula() throws FileNotFoundException {
        carregaDados();

        return null;
    }

    private void carregaDados() throws FileNotFoundException {
        stops =
                GTFSReader.loadStops("data/stops.txt");
        routes =
                GTFSReader.loadRoutes("data/routes.txt");
        shapes =
                GTFSReader.loadShapes("data/shapes.txt");
        services =
                GTFSReader.loadServices("data/calendar.txt");
        trips =
                GTFSReader.loadTrips("data/trips.txt",routes,services,shapes);
    }
}
