import java.awt.Color;

import biuoop.DrawSurface;

/**
 * class ColorSprite implements Sprite .
 */
public class ColorSprite implements Sprite {
    private Color color;

    /**
     * @param color put color.
     */
    public ColorSprite(Color color) {
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillRectangle(0, 0, 800, 600);
    }

    @Override
    public void timePassed(double dt, GameLevel gameLevel) {
    }

}
