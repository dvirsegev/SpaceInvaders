/**
 * @author dvir segev
 */

/**
 * Class CollisionInfo.
 */

public class CollisionInfo {
    private Point coolision;
    private Collidable collidable;

    /**
     * @return // the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.coolision;
    }

    /**
     * @param coolision  Point.
     * @param collidable collidable interface.
     */
    public CollisionInfo(Point coolision, Collidable collidable) {
        this.coolision = coolision;
        this.collidable = collidable;
    }


    /**
     * @return collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collidable;
    }
}
