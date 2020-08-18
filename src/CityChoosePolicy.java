
import java.util.ArrayList;


public interface CityChoosePolicy {
    City getRandomCity(ArrayList<City> cities);
    City getRandomNeighbourCity(City c);
}