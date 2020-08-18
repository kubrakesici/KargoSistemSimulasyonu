import java.lang.reflect.Array;
import java.util.ArrayList;

public class Truck {
    private String name;
    private City currentCity;
    private int weightCap;
    private int volumeCap;
    private int currentWeightCap;
    private int currentVolumeCap;
    private CityChoosePolicy cityChoosePolicy;
    private CargoCompany cargoCompany;
    private double fuelMoney;
    private double moneyCollected;
    private FuelCalculator fuelCalculator;
    private int distanceTaken;

    private ArrayList<Cargo> cargos;

    public Truck(int weight_cap, int volume_cap, City current_city,
                 CityChoosePolicy cityChoosePolicy, CargoCompany cargoCompany, FuelCalculator fuelCalculator) {
        this.name = "Truck";
        this.weightCap = weight_cap;
        this.volumeCap = volume_cap;
        this.currentCity = current_city;
        currentWeightCap = weightCap;
        currentVolumeCap = volumeCap;
        cargos = new ArrayList<>();
        this.cityChoosePolicy = cityChoosePolicy;
        this.cargoCompany = cargoCompany;
        this.fuelCalculator = fuelCalculator;
        checkCurrentCityCargos();
    }

    public int getCurrentWeight() {
        return weightCap - currentWeightCap;
    }

    public int getCurrentVolume() {
        return volumeCap - currentVolumeCap;
    }

    public boolean sendCargo(Truck truck) {
        for (Cargo c : cargos) {
            if (truck.addCargo(c)) {
                kickCargo(c.getId());
                return true;
            }
        }
        return false;
    }

    public void kickCargo(int id) {
        for (Cargo c : cargos) {
            if (c.getId() == id) {
                currentWeightCap += c.getWeight();
                currentVolumeCap += c.getVolume();
                cargos.remove(c);
                return;
            }
        }
    }

    public boolean addCargo(Cargo c) {
        if (currentWeightCap >= c.getWeight() && currentVolumeCap >= c.getVolume()) {
            currentWeightCap -= c.getWeight();
            currentVolumeCap -= c.getVolume();
            cargos.add(c);
            return true;
        }
        return false;
    }

    public void travel() {
        String cityName = currentCity.getName();
        setCurrentCity(cityChoosePolicy.getRandomNeighbourCity(currentCity));
        int distance = currentCity.getNeighbourCity(cityName).distance;
        distanceTaken += distance;
        fuelMoney += fuelCalculator.calculateFuelMoney(distance, getCurrentWeight());
        checkCurrentCityCargos();
    }

    public String toString() {
        String s = name + ":\n\tLocation: " + currentCity.toString() + "\n\tWeight capacity left: " + currentWeightCap +
                "\n\tVolume capacity left: " + currentVolumeCap + "\n\tCargos:\n";
        for (Cargo cargo : cargos) {
            s += cargo.toString() + "\n";
        }
        return s;

    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public void checkCurrentCityCargos() {
        ArrayList<Request> requests = cargoCompany.getRequests();
        for (int i = 0; i < requests.size(); i++) {
            Request r = requests.get(i);
            pickUpCargo(r);
            if (deliverCargo(r)) {
                i--;
            }
        }

    }

    public boolean pickUpCargo(Request r) {
        if (r.getFromC() != currentCity) return false;
        Cargo c = cargoCompany.getCargoByID(r.getCargoID());
        if (c.isPickedUp()) return false;
        if (addCargo(c)) {
            moneyCollected += cargoCompany.calculateCargoCharge(c);
            cargoCompany.takeCargo(c);
        }
        return true;
    }

    public boolean deliverCargo(Request r) {
        if (r.getToC() != currentCity) return false;
        for (Cargo c : cargos) {
            if (c.getId() == r.getCargoID()) {
                cargoCompany.completeRequest(r);
                kickCargo(c.getId());
                return true;
            }
        }
        return false;
    }

    public double getFuelMoney() {
        return fuelMoney;
    }

    public double getMoneyCollected() {
        return moneyCollected;
    }

    public int getDistanceTaken() {
        return distanceTaken;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
