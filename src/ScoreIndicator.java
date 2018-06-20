import java.awt.Color;

/**
 * public class ScoreIndicatior implements Sprite.
 */
public class ScoreIndicator implements Sprite {

    private Block block;
    private Counter counter;
    private LevelInformation gameLevel;

    /**
     * @param block            the block that get hit.
     * @param c                counter of score.
     * @param levelInformation the level.
     */
    public ScoreIndicator(Block block, Counter c, LevelInformation levelInformation) {
        this.block = block;
        counter = c;
        this.gameLevel = levelInformation;
    }

    /**
     * @param d draw the sprite to the screen.
     */
    public void drawOn(biuoop.DrawSurface d) {
        d.setColor(Color.GREEN);
        Rectangle rectangle = block.getCollisionRectangle();
        d.drawText((int) rectangle.getWidth() / 15, (int) rectangle.getHeight(),
                "Score: " + Integer.toString(counter.getValue()), 20);
        d.drawText((int) rectangle.getWidth() / 5 + 100, (int) rectangle.getHeight(),
                "Game: " + this.gameLevel.levelName(), 20);
    }

    /**
     * @param dt    the frame.
     * @param level the game.
     */

    public void timePassed(double dt, GameLevel level) {
    }
}
