import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
/**
 * @author dvir segev.
 */

/**
 * Class SpriteCollection.
 */
public class SpriteCollection {
    // list of spirtes object, for example: the balls, the paddle and the blocks.
    private List<Sprite> spirtes = new ArrayList<>();

    /**
     * @param s add s to the spriteCollection.
     */
    public void addSprite(Sprite s) {
        this.spirtes.add(s);
    }

    /**
     * call timePassed() on all sprites.
     *
     * @param dt        the notify of the frame.
     * @param gameLevel the game.
     */
    public void notifyAllTimePassed(double dt, GameLevel gameLevel) {
        for (int i = 0; i < spirtes.size(); i++) {
            if (this.spirtes.get(i) != null) {
                this.spirtes.get(i).timePassed(dt, gameLevel);
            }
        }
    }

    /**
     * @param d call drawOn(d) on all sprites.
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < spirtes.size(); i++) {
            if (spirtes.get(i) != null) {
                this.spirtes.get(i).drawOn(d);
            }
        }
    }

    /**
     * @param s remove from sprites list.
     */
    public void removeSprite(Sprite s) {
        this.spirtes.remove(s);
    }
}