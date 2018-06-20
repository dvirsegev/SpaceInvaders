import java.awt.Color;

/**
 * public calss LivesIndicatior that implements Sprite.
 */
public class LivesIndicator implements Sprite {
    private Block block;
    private Counter counter;

    /**
     * @param block   the down block.
     * @param counter how many lifes there is left.
     */
    public LivesIndicator(Block block, Counter counter) {
        this.block = block;
        this.counter = counter;
    }

    /**
     * @param d draw the sprite to the screen.
     */
    public void drawOn(biuoop.DrawSurface d) {
        d.setColor(Color.GREEN);
        Rectangle rectangle = block.getCollisionRectangle();
        d.drawText((int) rectangle.getWidth() / 2 + 200, (int) rectangle.getHeight(),
                "Lives: " + Integer.toString(counter.getValue()), 20);
    }

    /**
     * @param dt        the frame.
     * @param gameLevel the game.
     */
    public void timePassed(double dt, GameLevel gameLevel) {

    }
}
