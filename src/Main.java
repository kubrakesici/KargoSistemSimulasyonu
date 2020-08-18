import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\sejma\\IdeaProjects\\CargoSystem\\src\\cargoFile.txt");
        Scanner sc = new Scanner(file);
        int cityNum = Integer.parseInt(sc.nextLine());
        ArrayList<City> cities = new ArrayList<>();

        for (int i = 0; i < cityNum; i++) {
            String name = sc.nextLine();
            cities.add(new City(name));
        }
        int neighbourCityNum = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < neighbourCityNum; i++) {
            String neighbourInformationLine = sc.nextLine();
            String[] neighbourInformation = neighbourInformationLine.split(" ");
            City c1 = getCityByName(cities, neighbourInformation[0]);
            City c2 = getCityByName(cities, neighbourInformation[1]);
            int distance = Integer.parseInt(neighbourInformation[2]);
            c1.addNeighbourCity(c2, distance);
        }
        String trucksInformationLine = sc.nextLine();
        String[] trucksInformation = trucksInformationLine.split(" ");
        int truckNum = Integer.parseInt(trucksInformation[0]);
        int truckWeightCapacity = Integer.parseInt(trucksInformation[1]);
        int truckVolumeCapacity = Integer.parseInt(trucksInformation[2]);

        String constantsInformationLine = sc.nextLine();
        String[] constantsInformation = constantsInformationLine.split(" ");

        double LPK0 = Double.parseDouble(constantsInformation[0]);
        double MPL = Double.parseDouble(constantsInformation[1]);
        double C0 = Double.parseDouble(constantsInformation[2]);
        double UCCw = Double.parseDouble(constantsInformation[3]);
        double UCCv = Double.parseDouble(constantsInformation[4]);
        int maxDay = Integer.parseInt(sc.nextLine());

        int requestsNum = Integer.parseInt(sc.nextLine());
        ArrayList<Request> requests = new ArrayList<>();
        ArrayList<Cargo> cargos = new ArrayList<>();

        for (int i = 0; i < requestsNum; i++) {
            String requestsInformationLine = sc.nextLine();
            String[] requestsInformation = requestsInformationLine.split(" ");
            int requestedDay = Integer.parseInt(requestsInformation[0]);
            City c1 = getCityByName(cities, requestsInformation[1]);
            City c2 = getCityByName(cities, requestsInformation[2]);
            int cargoWeight = Integer.parseInt(requestsInformation[3]);
            int cargoVolume = Integer.parseInt(requestsInformation[4]);
            Cargo newCargo = new Cargo(cargoWeight, cargoVolume);
            requests.add(new Request(newCargo.getId(), requestedDay, c1, c2));
            cargos.add(newCargo);
        }
        FuelCalculator fc = new FuelCalculator(MPL, LPK0);
        CargoCompany cargoCompany = new CargoCompany(cities, requests, cargos, truckNum, truckWeightCapacity, truckVolumeCapacity, fc,
                C0, UCCw, UCCv);
        Simulator s = new Simulator(cargoCompany);
        s.setUpdateInterval(0);
        s.setSilentSimulation(true);
        s.simulate(maxDay);
    }

    public static City getCityByName(ArrayList<City> cities, String name) {
        for (City city : cities) {
            if (city.getName().equals(name)) return city;
        }
        return null;
    }
}