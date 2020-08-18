import java.io.IOException;

/**
 * Created by dindar.oz on 4/10/2018.
 */
public class DisplayUtil {


    // This function clears the output screen.
    public static final void clearScreen()
    {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void pause(int millisecond)
    {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
