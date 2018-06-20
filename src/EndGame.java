import java.awt.Color;

/**
 * public class EndGmae implements Animation.
 */
public class EndGame implements Animation {

    private boolean stop;
    private Counter score;

    /**
     * @param score the score.
     */
    public EndGame(Counter score) {
        this.stop = false;
        this.score = score;
    }

    /**
     * @param d  doOneFrame(DrawSurface) is in charge of the logic.
     * @param dt the frame.
     */
    public void doOneFrame(biuoop.DrawSurface d, double dt) {
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.BLACK);
        d.drawText(100, d.getHeight() / 2, " Game Over Loser!! your score:  " + score.getValue(), 32);
        d.drawText(100, d.getHeight() / 2 + 100, " press space to exit program", 32);
    }

    /**
     * @return true or false.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}

