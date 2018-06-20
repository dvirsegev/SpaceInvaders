/**
 * @author dvir segev
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Class GameEnviroment.
 */

public class GameEnvironment {
    // list of collidables object. for example: paddle and blocks.
    private List<Collidable> collidebles = new ArrayList();

    /**
     * @param c coliidable.
     */
    public void addCollidable(Collidable c) {
        //add the given collidable to the environment.
        this.collidebles.add(c);
    }

    /**
     * @param c Remove c from collidebles list.
     */
    public void removeCollidable(Collidable c) {
        this.collidebles.remove(c);
    }

    /**
     * @param points     List of points.
     * @param trajectory of the ball.
     * @return the closest Point collision.
     */
    public Point closestPoint(List points, Line trajectory) {
        // the number min isn't important, it is just for choose the first min.
        double min = 1000000;
        Point closest = null;
        // find the point that have the minimum distance with the trajectory.
        for (int i = 0; i < points.size(); i++) {
            Point point = (Point) points.get(i);
            if (point == null) {
                continue;
            }
            // if the distance between trajecotory and the collision point is lower then the min, set to be the min dis.
            if (min > trajectory.start().distance(point)) {
                min = trajectory.start().distance(point);
                // set the point to be the minimum.
                closest = point;
            }
        }
        return closest;
    }


    /**
     * @param trajectory of the ball
     * @return Collision Info of the closest collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List points = new ArrayList();
        Collidable colli = null;
        for (int i = 0; i < collidebles.size(); i++) {
            Collidable c = collidebles.get(i);
            // list of points intersection.
            Point p1 = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            points.add(p1);
        }
        // if list is empty.
        if (points == null) {
            return null;
        }
        // Check in the function closesPoint who is the closest point to the trajecory.
        Point closest = closestPoint(points, trajectory);
        if (closest == null) {
            return null;
        }
        // check which rectangle is have the closes point.
        colli = this.collidebles.get(points.indexOf(closest));
        // return the Collision object with the closest point.
        return new CollisionInfo(closest, colli);

    }

}
