/**
 * @author dvir segev.
 */

/**
 * Class Velocity.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * @param dx1 double
     * @param dy1 double
     */
    public Velocity(double dx1, double dy1) {
        this.dx = dx1;
        this.dy = dy1;
    }

    /**
     * @param angle angle.
     * @param speed speed.
     * @return new Velocity
     */
    public Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx1 = speed * Math.sin(Math.toRadians(angle));
        double dy1 = speed * (Math.cos(Math.toRadians(angle)));
        return new Velocity(dx1, dy1);
    }

    /**
     * @return the absolute speed.
     */
    public double speedHit() {
        double absoluteSpeed = Math.sqrt((this.dx * this.dx) + (this.dy * this.dy));
        return absoluteSpeed;
    }

    /**
     * @param p Point.
     * @return Take a point with position (x,y) and return a new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p) {
        double x = this.dx + p.getX();
        double y = this.dy + p.getY();
        return new Point(x, y);
    }

    /**
     * @param x set new speed in the width.
     */
    public void setDx(double x) {
        this.dx = x;
    }

    /**
     * @param y * @param x set new speed in the height.
     */
    public void setDy(double y) {
        this.dy = y;
    }

    /**
     * @return the speed in the width.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return the speed in the height.
     */
    public double getDy() {
        return this.dy;
    }
}
