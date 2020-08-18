public class FuelCalculator {
    private double MPL;
    private double LPK0;

    public FuelCalculator(double MPL, double LPK0) {
        this.MPL = MPL;
        this.LPK0 = LPK0;
    }

    public double calculateFuelMoney(int d, int W){
        double LPK = LPK0 * (1 + W / 1000);
        return LPK * MPL * d;
    }
}
