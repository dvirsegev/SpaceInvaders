import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;


/**
 * public class PauseScreen that implements Animation.
 */
public class PauseScreen implements Animation {
    private static final int START = 0;

    /**
     * @param d  doOneFrame(DrawSurface) is in charge of the logic.
     * @param dt the frame.
     */
    public void doOneFrame(biuoop.DrawSurface d, double dt) {
        Image image = null;
        try {
            // image stop.P
            image = ImageIO.read(new File("resources/background_images/stop.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        d.drawImage(START, START, image);
        d.setColor(Color.BLACK);
        d.drawText(150, (int) (d.getHeight() / 9), "paused -- press space to continue", 32);
    }

    /**
     * @return true or false.
     */
    public boolean shouldStop() {
        return false;
    }
}