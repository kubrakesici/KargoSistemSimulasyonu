import java.util.ArrayList;

public class SimpleCEP implements CargoExchangePolicy {

    @Override
    public void exchangeCargos(ArrayList<Truck> trucks) {
        for(int i = 0; i < trucks.size(); i++){
            Truck t1 = trucks.get(i);
            for(int j = i + 1; j < trucks.size(); j++){
                Truck t2 = trucks.get(j);
                if(t1.getCurrentCity().getName().equals(t2.getCurrentCity().getName())){
                    exchangeTruckCargos(t1, t2);
                }
            }
        }
    }

    private void exchangeTruckCargos(Truck t1, Truck t2){
        if(t1.getCurrentWeight() + t1.getCurrentVolume() > t2.getCurrentWeight() + t2.getCurrentVolume()){
            t1.sendCargo(t2);
        }
    }
}
