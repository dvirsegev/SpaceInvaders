import biuoop.DrawSurface;

/**
 * @author dvir segev.
 */

/**
 * interface Sprite.
 */

public interface Sprite {

    /**
     * @param d draw the sprite to the screen.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     * @param dt specifies the amount of seconds passed since the last call.
     * @param gameLevel the game.
     */

    void timePassed(double dt, GameLevel gameLevel);
}