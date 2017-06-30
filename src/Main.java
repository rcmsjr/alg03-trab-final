import smartcity.gtfs.Route;
import smartcity.util.GPSCoordinate;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by thiago on 30/06/17.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Rota rota = new Rota();

        System.out.println("Informe as coordenadas de origem (lat, long):");
        double latOrigem = scanner.nextDouble();
        double longOrigem = scanner.nextDouble();

        rota.setOrigem(new GPSCoordinate(latOrigem, longOrigem));

        System.out.println("Informe as coordenadas de destino (lat, long):");
        double latDest = scanner.nextDouble();
        double longDest = scanner.nextDouble();

        rota.setDestino(new GPSCoordinate(latDest, longDest));

        ArrayList<Route> paradas = rota.calcula();
    }
}
