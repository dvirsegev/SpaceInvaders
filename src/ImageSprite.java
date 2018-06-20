import java.awt.Image;

import biuoop.DrawSurface;

/**
 * class ImageSprite implements Sprite.
 */
public class ImageSprite implements Sprite {

    private Image image;
    private static final int STARTINDEX = 0;

    /**
     * @param image put image.
     */
    public ImageSprite(Image image) {
        this.image = image;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.drawImage(STARTINDEX, STARTINDEX, image); // draw the image at location 0, 0.
    }

    @Override
    public void timePassed(double dt, GameLevel gameLevel) {
    }

}
