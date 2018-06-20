import biuoop.DrawSurface;

import java.awt.Color;

/**
 * @author dvir segev.
 */

/**
 * Class Ball.
 */
public class Ball implements Sprite {
    // radius of the ball
    private int radius;
    // the ball.
    private Point center;
    // color of the ball
    private Color color;
    // the speed of the ball.
    private Velocity vel;
    private GameEnvironment game;

    /**
     * @param x     start index of the ball
     * @param y     start index of the ball
     * @param r     radius of the ball.
     * @param color color.
     * @param game  add to the collision.
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment game) {
        this.color = color;
        this.radius = r;
        this.vel = new Velocity(0, 0);
        this.center = new Point(x, y);
        this.game = game;
    }

    /**
     * @return color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * @param surface draw the ball.
     */

    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);

    }

    /**
     * @param v Velocity- set the veloctiy of the ball.
     */
    public void setVelocity(Velocity v) {
        this.vel = v;
    }

    /**
     * @param dx set the speed of the ball.
     * @param dy set the speed of the ball.
     */
    public void setVelocity(double dx, double dy) {
        this.vel.setDx(dx);
        this.vel.setDy(dy);
    }

    /**
     * function of how to direct the ball.
     * Important comment! i did the limits according to the Center of the ball! That is mean the ball can be
     * out of the frame until the center of the ball meet the limits.
     *
     * @param dt        the frame.
     * @param gameLevel the game.
     */
    public void moveOneStep(double dt, GameLevel gameLevel) {
        // intilize the speed that the user set.
        Point appliedPoint = this.center;
        Point point = new Point(0, 0);
        // make the move according to the radius.
        if (this.vel.getDx() >= 0) {
            point.setX(appliedPoint.getX() + vel.getDx() * dt + radius);
        } else if (this.vel.getDx() < 0) {
            point.setX(appliedPoint.getX() + vel.getDx() * dt - radius);
        }
        if (this.vel.getDy() >= 0) {
            point.setY(appliedPoint.getY() + vel.getDy() * dt + radius);
        } else if (this.vel.getDy() < 0) {
            point.setY(appliedPoint.getY() + vel.getDy() * dt - radius);
        }
        // set the trajeocory
        Line trajecory = new Line(this.center, point);
        // check if there is Collision.
        CollisionInfo info = this.game.getClosestCollision(trajecory);
        // if there is collision.
        if (info != null) {
            // the center occur the collision.
            Point p = info.collisionPoint();
            // set new speed.
            this.vel = info.collisionObject().hit(this, p, this.vel);
            gameLevel.removeSprite(this);
        }
        // set the speed with the dt.
        double dx = vel.getDx() * dt;
        double dy = vel.getDy() * dt;
        this.center = new Velocity(dx, dy).applyToPoint(center);
    }

    /**
     * move to the function move one step.
     *
     * @param dt        the frame.
     * @param gameLevel the game.
     */

    public void timePassed(double dt, GameLevel gameLevel) {
        moveOneStep(dt, gameLevel);
    }

    /**
     * @param g type of Ass3Game. add the ball to the sprite collection.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * @param g remove from the Game.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

}

