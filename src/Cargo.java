public class Cargo {
    private int id;
    private int weight;
    private int volume;
    private int pickUpDay;
    private boolean pickedUp;
    private static int id_counter = 0;

    public Cargo(int weight, int volume) {
        this.id = id_counter++;
        this.weight = weight;
        this.volume = volume;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getVolume() {
        return volume;
    }
    public void setVolume(int volume) {
        this.volume = volume;
    }
    public int getId() {
        return id;
    }
    public String toString(){
        return "\tID: " + id + "\n\tWeight: " + weight + "\n\tVolume: " + volume;
    }

    public int getPickUpDay() {
        return pickUpDay;
    }

    public void setPickUpDay(int pickUpDay) {
        this.pickUpDay = pickUpDay;
        pickedUp = true;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }
}
