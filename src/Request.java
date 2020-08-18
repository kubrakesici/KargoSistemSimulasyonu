public class Request {
    private int day;
    private int cargoID;
    private  City fromC;
    private City toC;

    public Request(int cargoID, int day, City fromC, City toC) {
        this.cargoID = cargoID;
        this.day = day;
        this.fromC = fromC;
        this.toC = toC;
    }
    public int getCargoID() {
        return cargoID;
    }

    public void setCargoID(int cargoID) {
        this.cargoID = cargoID;
    }

    public City getFromC() {
        return fromC;
    }

    public void setFromC(City fromC) {
        this.fromC = fromC;
    }

    public City getToC() {
        return toC;
    }

    public void setToC(City toC) {
        this.toC = toC;
    }

    public String toString(){
        return "\t\tCargo ID: " + cargoID + "\n\t\tFromCity: " + fromC.getName() + "\n\t\tToCity: " + toC.getName();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
