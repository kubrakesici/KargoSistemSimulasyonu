import java.lang.reflect.Array;
import java.util.ArrayList;

public class CargoCompany implements Simulatable {
    private CityChoosePolicy cityChoosePolicy;
    private CargoExchangePolicy cargoExchangePolicy;
    private ArrayList<Truck> trucks;
    private ArrayList<City> cities;
    private ArrayList<Cargo> cargos;
    private ArrayList<Request> requests;

    private int currentDay;
    private int totalDeliveredCargos;
    private int totalServiceTime;
    private int totalDeliveryTime;
    private double C0, UCCw, UCCv;

    public CargoCompany(ArrayList<City> cities, ArrayList<Request> requests, ArrayList<Cargo> cargos, int truckNum, int truckWeightCap, int truckVolumeCap,
            FuelCalculator fc, double C0, double UCCw, double UCCv) {
        this.C0 = C0;
        this.UCCw = UCCw;
        this.UCCv = UCCv;
        trucks = new ArrayList<>();
        cityChoosePolicy = new SimpleCCP();
        cargoExchangePolicy = new SimpleCEP();
        this.cities = cities;
        this.cargos = cargos;
        this.requests = requests;
        for(int i = 0; i < truckNum; i++) {
            String truckName = "Truck"+Integer.toString(i + 1);
            trucks.add(new Truck(truckWeightCap, truckVolumeCap, cityChoosePolicy.getRandomCity(cities), cityChoosePolicy, this, fc));
            trucks.get(i).setName(truckName);
        }
    }

    public Cargo getCargoByID(int id) {
        for (Cargo c : cargos) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public ArrayList<Request> getRequests() {
        ArrayList<Request> requests = new ArrayList<>();
        for (Request request : this.requests) {
            if (request.getDay() <= currentDay) {
                requests.add(request);
            }
        }
        return requests;
    }

    public ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public void completeRequest(Request request) {
        totalServiceTime += currentDay - request.getDay();
        totalDeliveryTime += currentDay - getCargoByID(request.getCargoID()).getPickUpDay();

        //add money
        totalDeliveredCargos++;
        requests.remove(request);
    }

    public void takeCargo(Cargo c) {
        c.setPickUpDay(currentDay);
    }

    public String toString() {
        String s = "Requests:\n";
        for (Request r : requests) {
            s += r.toString() + "\n";
        }
        return s;
    }

    @Override
    public void update(int day) {
        currentDay = day;
        for (Truck truck : trucks) {
            System.out.println("Before Travel........................");
            System.out.println(truck);

            truck.travel();
            cargoExchangePolicy.exchangeCargos(trucks);
            System.out.println("After Travel........................");
            System.out.println(truck);
            System.out.println("Current Requests....................");
            System.out.println(this);
            System.out.println(truck.getName() + " total distance of  truck  " + truck.getDistanceTaken());
            System.out.println(truck.getName() + " total money of  truck  " + truck.getMoneyCollected());
            System.out.println();
        }
    }

    @Override
    public void print(int day) {
        System.out.println("Total Cargos Collected: " + totalDeliveredCargos);
        System.out.println("Average Service Time (Day): " + getAverageServiceTime());
        System.out.println("Average Delivery Time (Day): " + getAverageDeliveryTime());
        System.out.println("Spent Money For Fuel: " + getTotalFuelMoney());
        System.out.println("Collected Money From Cargo Charges: " + getTotalCargoCharge());
        System.out.println("Total Taken Distance: " + getTotalTakenDistance() + "KM");
        System.out.println("Average money for fuel per kilometer " + getMoneyPerKilometer());
    }

    @Override
    public void init(int day) {

    }

    public int getTotalDeliveredCargos() {
        return totalDeliveredCargos;
    }

    public void setTotalDeliveredCargos(int totalDeliveredCargos) {
        this.totalDeliveredCargos = totalDeliveredCargos;
    }

    public double getMoneyPerKilometer() {
        return getTotalFuelMoney() / getTotalTakenDistance();
    }

    public int getTotalTakenDistance() {
        int distanceTaken = 0;
        for (Truck truck : trucks) {
            distanceTaken += truck.getDistanceTaken();
        }
        return distanceTaken;
    }

    public int getAverageDeliveryTime() {
        if(totalDeliveredCargos == 0) return 0;
        return totalDeliveryTime / totalDeliveredCargos;
    }

    public int getAverageServiceTime() {
        if(totalDeliveredCargos == 0) return 0;
        return totalServiceTime / totalDeliveredCargos;
    }

    public double getTotalFuelMoney() {
        double totalFuelMoney = 0;
        for (Truck truck : trucks) {
            totalFuelMoney += truck.getFuelMoney();
        }
        return totalFuelMoney;
    }

    public double getTotalCargoCharge() {
        double totalCargoCharge = 0;
        for (Truck truck : trucks) {
            totalCargoCharge += truck.getMoneyCollected();
        }
        return totalCargoCharge;
    }

    public double calculateCargoCharge(Cargo c) {
        return C0 + UCCw * c.getWeight() + UCCv * c.getVolume();
    }
}