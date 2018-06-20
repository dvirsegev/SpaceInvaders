/**
 * @author dvir segev.
 */

/**
 * interface Collidable.
 */
public interface Collidable {
    /**
     * @return Return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * @param collisionPoint  collisionPoint.
     * @param currentVelocity the speed of the ball.
     * @param hitter the ball.
     * @return the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

}
