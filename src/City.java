
import java.util.ArrayList;


public class City {
    private String name;
    private ArrayList<NeighbourCity> neighbourCities;

    public class NeighbourCity {
        City neighbourCity;
        int distance;

        public NeighbourCity(City neighbourCity, int distance) {
            this.neighbourCity = neighbourCity;
            this.distance = distance;
        }
    }

    public City(String name) {
        this.name = name;
        neighbourCities = new ArrayList<>();
    }

    public void addNeighbourCity(City c, int d) {
        addNeighbourCityHepler(c,d);
        c.addNeighbourCityHepler(this,d);
    }
    private void addNeighbourCityHepler(City c, int d){
        neighbourCities.add(new NeighbourCity(c, d));
    }

    public NeighbourCity getNeighbourCity(String name) {
        for (NeighbourCity nc : neighbourCities) {
            if (nc.neighbourCity.getName() == name) return nc;
        }
        return null;
    }

    public ArrayList<NeighbourCity> getNeighbourCitiesWithDistances() {
        return neighbourCities;
    }

    public ArrayList<City> getNeighbourCities(){
        ArrayList<City> neighbourCities = new ArrayList<>();
        for (NeighbourCity neighbourCity:this.neighbourCities) {
            neighbourCities.add(neighbourCity.neighbourCity);
        }
        return neighbourCities;
    }

    @Override
    public String toString() {
        String s="";
        s+=name+"\n\tNeighbours:\n";
        for (NeighbourCity nc:neighbourCities) {
            s+="\t\tName: "+nc.neighbourCity.getName()+", Distance: "+nc.distance+"\n";
        }
        return s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
