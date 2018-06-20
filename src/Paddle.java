import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * public class Paddle that implement Sprite and Collidable interfaceses.
 */
public class Paddle implements Sprite, Collidable, HitNotifier {
    private biuoop.KeyboardSensor keyboard;
    private Block paddle;
    private Color color;
    private int speed;
    private int widthpad;
    private static final int LOWLIMIT = 15;
    private static final int HIGHTLIMIT = 785;
    private int heightpad = 20;
    //private Ball ball;
    //private GameEnvironment game;
    private List<HitListener> hitListenerList = new ArrayList<>();

    /**
     * @param sensor          KeyboardSensor. Constractor of the Paddel.
     * @param gameEnvironment the game.
     */
    public Paddle(KeyboardSensor sensor, GameEnvironment gameEnvironment) {
        final int randColor = (int) (Math.random() * 0x1000000);
        this.keyboard = sensor;
        Point pointStartScreen = new Point(300, 570);
        this.widthpad = 200;
        this.color = new Color(randColor);
        //this.game = gameEnvironment;
        this.paddle = new Block(new Rectangle(pointStartScreen, widthpad, heightpad), this.color);
        //this.ball = new Ball((300 + widthpad) / 2, (570 + widthpad), 6, Color.WHITE, this.game);
    }

    /**
     * @param widthpad1 set Width to the paddle.
     */
    public void setWidthpad(int widthpad1) {
        this.widthpad = widthpad1;
        this.paddle = new Block(new Rectangle(new Point(300, 570), widthpad, heightpad), this.color);
    }

    /**
     * @return the block.
     */
    public Block returnBlock() {
        Point pointStartScreen = new Point(300, 570);
        return new Block(new Rectangle(pointStartScreen, widthpad, heightpad), this.color);
    }

    /**
     * @return width of the paddle.
     */
    public int getWidthpad() {
        return widthpad;
    }

    /**
     * @param speed1 the speed.
     */
    public void setSpeed(int speed1) {
        this.speed = speed1;
    }

    /**
     * behavior pattern of move the paddle left.
     *
     * @param dt the frame.
     */
    public void moveLeft(double dt) {
        double dtspeed = speed * dt;
        Rectangle recPaddle = this.paddle.getCollisionRectangle();
        /* check if the left top point is reached to the limit of the screen. if not, move each
        corners of the rectangle steps to the left.
        */
        if (recPaddle.getUpperLeft().getX() >= LOWLIMIT) {
            recPaddle.getUpperLeft().setnewPoint(recPaddle.getUpperLeft().getX() - dtspeed,
                    recPaddle.getUpperLeft().getY());
            recPaddle.getDownLeft().setnewPoint(recPaddle.getDownLeft().getX() - dtspeed,
                    recPaddle.getDownLeft().getY());
            recPaddle.getDownRight().setnewPoint(recPaddle.getDownRight().getX() - dtspeed,
                    recPaddle.getDownRight().getY());
            recPaddle.getUpperRight().setnewPoint(recPaddle.getUpperRight().getX() - dtspeed,
                    recPaddle.getUpperRight().getY());
        }
    }

    /**
     * * behavior pattern of move the paddle right.
     *
     * @param dt the frame.
     */
    public void moveRight(double dt) {
        Rectangle recPaddle = this.paddle.getCollisionRectangle();
        /* check if the right top point is reached to the limit of the screen. if not, move each
        corners of the rectangle steps to the right.
        */
        double dtspeed = speed * dt;
        if (recPaddle.getUpperRight().getX() <= HIGHTLIMIT) {
            recPaddle.getUpperLeft().setnewPoint(recPaddle.getUpperLeft().getX() + dtspeed,
                    recPaddle.getUpperLeft().getY());
            recPaddle.getDownLeft().setnewPoint(recPaddle.getDownLeft().getX() + dtspeed,
                    recPaddle.getDownLeft().getY());
            recPaddle.getDownRight().setnewPoint(recPaddle.getDownRight().getX() + dtspeed,
                    recPaddle.getDownRight().getY());
            recPaddle.getUpperRight().setnewPoint(recPaddle.getUpperRight().getX() + dtspeed,
                    recPaddle.getUpperRight().getY());
        }
    }

    /**
     * Check What Keyboard is press.
     *
     * @param dt        the frame.
     * @param gameLevel the game
     */
    public void timePassed(double dt, GameLevel gameLevel) {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY) || (this.keyboard.isPressed("a"))) {
            moveLeft(dt);
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY) || (this.keyboard.isPressed("d"))) {
            moveRight(dt);
        }
    }


    /**
     * @param d DrawSurface.
     * @apiNote draw the paddle.
     */
    public void drawOn(DrawSurface d) {
        // set Color for the edge of the rectangle.
        d.setColor(Color.BLACK);
        // draw the edge of the rectangle in black.
        d.drawRectangle((int) this.getCollisionRectangle().getUpperLeft().getX(),
                (int) this.getCollisionRectangle().getUpperLeft().getY(),
                (int) this.getCollisionRectangle().getWidth(), (int) this.getCollisionRectangle().getHeight());
        // set the Color for the paddle himself
        d.setColor(this.color);
        // fill the paddle with this color.
        d.fillRectangle((int) this.getCollisionRectangle().getUpperLeft().getX(),
                (int) this.getCollisionRectangle().getUpperLeft().getY(),
                (int) this.getCollisionRectangle().getWidth(),
                (int) this.getCollisionRectangle().getHeight());
    }

    /**
     * @return the paddle.
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle.getCollisionRectangle();
    }

    /**
     * @param collisionPoint  the collision point.
     * @param currentVelocity the speed of the alienShot.
     * @param alienShot       the alienShot.
     * @return new velocity if there is a hit between the paddle and the alienShot. else return the regular veloctiy.
     */
    public Velocity hit(Ball alienShot, Point collisionPoint, Velocity currentVelocity) {
        // notify to the listener about the hit.
        this.notifyHit(alienShot);
        return currentVelocity;
    }

    /**
     * @param alienShot the shot that hit the paddle.
     */
    private void notifyHit(Ball alienShot) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListenerList);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this.returnBlock(), alienShot);
        }
    }

    /**
     * @param g GameLevel type. Add the Paddle to the GameLevel.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * @param hl Add hl as a listener to hit events.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListenerList.add(hl);
    }

    /**
     * @param hl Remove hl from the list of listeners to hit events.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListenerList.remove(hl);
    }
}
