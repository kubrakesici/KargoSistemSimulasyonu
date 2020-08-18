import java.util.ArrayList;
import java.util.Random;

public class SimpleCCP implements CityChoosePolicy {

    @Override
    public City getRandomCity(ArrayList<City> cities) {
        Random rand = new Random();
        return cities.get(rand.nextInt(cities.size()));
    }

    @Override
    public City getRandomNeighbourCity(City c) {
        Random rand = new Random();
        ArrayList<City> neighbourCities = c.getNeighbourCities();
        return neighbourCities.get(rand.nextInt(neighbourCities.size()));
    }
}

