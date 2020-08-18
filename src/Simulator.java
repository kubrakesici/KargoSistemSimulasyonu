/**
 * Created by dindar.oz on 4/2/2018.
 */
public class Simulator {

    public static final int INITIAL_DAY=0;

    private Simulatable system;
    private int day;
    private boolean silentSimulation;
    private int updateInterval;


    public Simulator(Simulatable system) {
        this.system = system;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    public void simulate(int maxDay)
    {
        init();

        while (day < maxDay)
        {
            system.update(day);
            if (!silentSimulation) {
                System.out.println("CLOCK:" + day + "\n\n");
                system.print(day);
            }
            if (updateInterval > 0)
                DisplayUtil.pause(updateInterval);

            day++;

        }
        system.print(day);
    }

    private void init() {
        day = INITIAL_DAY;
        system.init(day);
    }


    public void setSilentSimulation(boolean silentSimulation) {
        this.silentSimulation = silentSimulation;
    }
}
