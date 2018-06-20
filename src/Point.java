/**
 * @author dvir segev.
 * Class of Point.
 */
public class Point {
    private double x;
    private double y;

    /**
     * @param x type of parameter.
     * @param y type of parameter.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param other type of point.
     * @return distance between the points.
     */
    public double distance(Point other) {
        // formulae of distance between 2 Points.
        double distanceX = (this.x - other.x) * (this.x - other.x);
        double distanceY = (this.y - other.y) * (this.y - other.y);
        distanceX = Math.abs(distanceX);
        distanceY = Math.abs(distanceY);
        return Math.sqrt(distanceX + distanceY);
    }

    /**
     * @param other Point.
     * @return return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        // if they exactly the same point return true else return false.
        return ((this.getX() == other.getX()) && (this.getY() == other.getY()))
                || ((this.getY() == other.getX()) && (this.getX() == other.getY()));
    }

    /**
     * @return the location x of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return the location y of the point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * @param y1 Y of the Point.
     */
    public void setY(double y1) {
        this.y = y1;
    }

    /**
     * @param x1 X of the Point.
     */
    public void setX(double x1) {
        this.x = x1;
    }

    /**
     * set new Point.
     *
     * @param x1 x of the point.
     * @param y1 y of the point.
     */
    public void setnewPoint(double x1, double y1) {
        this.x = x1;
        this.y = y1;
    }
}
