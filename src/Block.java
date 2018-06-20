/**
 * @author dvir segev
 */

import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class Block -using Collidable, Spirte, HitNotifier.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle blockTangle;
    private Color color;
    private int lifeBlock;
    private Color storke = null;
    private List<HitListener> hitListeners;
    private Map<Integer, Color> colors;
    private Map<Integer, Image> images;

    /**
     * @param blockTangle shape rectangle,
     * @param color       color
     */
    public Block(Rectangle blockTangle, Color color) {
        this.blockTangle = blockTangle;
        this.color = color;
        hitListeners = new ArrayList<>();
    }

    /**
     * @param blockTangle shape rectangle,
     * @param color       color
     * @param storke      the stroke.
     */
    public Block(Rectangle blockTangle, Color color, Color storke) {
        this.blockTangle = blockTangle;
        this.color = color;
        hitListeners = new ArrayList<>();
        this.storke = storke;
    }

    /**
     * @return the block.
     */
    public Rectangle getCollisionRectangle() {
        return this.blockTangle;
    }

    /**
     * @param collisionPoint collision point.
     * @param line           of the ball
     * @return if the collision point is on the line return true, else return fasle.
     */
    public boolean checkPoint(Point collisionPoint, Line line) {
        /* if the distance between the collision point and the start is + the distance between
        the collision point and the end point equlas to the distance between the start and the end it's' mean that
                the point is on the line. */
        return (collisionPoint.distance(line.start()) + collisionPoint.distance(line.end())
                == line.start().distance(line.end()));
    }

    /**
     * @param collisionPoint  collision Point
     * @param currentVelocity the velocity of the ball.
     * @param hitter          the ball.
     * @return new Velocity.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // define the block.
        Line upperRib = blockTangle.getUpperRib();
        Line leftRib = blockTangle.getLeftRib();
        Line downRib = blockTangle.getDownRib();
        Line rightRib = blockTangle.getRightRib();
        // if cloosion point is on upperRib or downRib change the velocity.
        if ((checkPoint(collisionPoint, upperRib)) || (checkPoint(collisionPoint, downRib))) {
            currentVelocity.setDy(-1 * currentVelocity.getDy());
        }
        // if cloosion point is on right_rib or left_rib change the velocity.
        if ((checkPoint(collisionPoint, leftRib)) || (checkPoint(collisionPoint, rightRib))) {
            currentVelocity.setDx(-1 * currentVelocity.getDx());

        }
        // down the number by 1 that show on the block.
        setHit(this.lifeBlock - 1);
        this.notifyHit(hitter);
        return currentVelocity;
    }

    /**
     * @param d DrawSurface type. draw the Block.
     */
    public void drawOn(DrawSurface d) {
        if (this.blockTangle.getUpperLeft() == null) {
            return;
        }
        // map of color and life block if there is fill-k.
        Color drawColor = (colors != null) ? colors.get(lifeBlock) : null;
        // map of Images and life block if there is fill-k.
        Image image1 = (images != null) ? images.get(lifeBlock) : null;
        if (drawColor == null) {
            drawColor = color;
        }
        if (storke != null) {
            d.setColor(this.storke);
            d.drawRectangle((int) this.blockTangle.getUpperLeft().getX(), (int) this.blockTangle.getUpperLeft().getY(),
                    (int) this.blockTangle.getWidth(), (int) this.blockTangle.getHeight());
        }
        if (drawColor != null) {
            d.setColor(drawColor);
            // fill the blocks with the color that the user choose.
            d.fillRectangle((int) this.blockTangle.getUpperLeft().getX(), (int) this.blockTangle.getUpperLeft().getY(),
                    (int) this.blockTangle.getWidth(), (int) this.blockTangle.getHeight());
        }
        d.setColor(Color.WHITE);
        // if there is any fill-k with image.
        if (image1 != null) {
            d.drawImage((int) this.blockTangle.getUpperLeft().getX(),
                    (int) this.blockTangle.getUpperLeft().getY(), image1);
            // if there is image regular.

        }


    }

    /**
     * @param number the number that should be on the block.
     */
    public void setHit(int number) {
        this.lifeBlock = number;
    }

    /**
     * @param dt        the frame.
     * @param gameLevel the game.
     */
    public void timePassed(double dt, GameLevel gameLevel) {

    }

    /**
     * @param g GameLevel type. add the block to the Collidable and Sprite Lists.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * @param gameLevel the gameLevel itself.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * @param hl type of HitListener.
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);

    }

    /**
     * @param hl type of HitListener.
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * @param hitter notify the hit.
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * @return the "life" of the block.
     */
    public int getHitPoints() {
        return this.lifeBlock;
    }

    /**
     * @param colors1 set map of colors and integer.
     */
    public void setColors(Map<Integer, Color> colors1) {
        this.colors = colors1;

    }

    /**
     * @param imageMap set map of Images.
     */
    public void setImages(Map<Integer, Image> imageMap) {
        this.images = imageMap;
    }

    /**
     * @param color1 the color.
     */
    public void setColor(Color color1) {
        this.color = color1;
    }

    /**
     * @param gameLevel set the block to the game.
     */
    public void setSpirte(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
}
