import java.util.List;

/**
 * @author dvir segev
 */

/**
 * Class Line.
 */
public class Line {
    private Point point1;
    private Point point2;

    /**
     * @param start start of the line .
     * @param end   end of the line.
     */
    public Line(Point start, Point end) {
        this.point1 = start;
        this.point2 = end;
    }

    /**
     * @param x1 Variable X1
     * @param y1 Variable Y1
     * @param x2 Variable X2
     * @param y2 Variable Y2
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.point1 = new Point(x1, y1);
        this.point2 = new Point(x2, y2);
    }

    /**
     * @return the length of the line.
     */
    public double length() {
        return this.point2.distance(point1);
    }

    /**
     * @return middle of the line.
     */
    public Point middle() {
        Point p3;
        p3 = new Point((point1.getX() + point2.getX()) / 2, (point1.getY() + point2.getY()) / 2);
        return p3;
    }

    /**
     * @return the start point of the line.
     */
    public Point start() {
        return this.point1;
    }

    /**
     * @return the end point of the line.
     */
    public Point end() {
        return this.point2;
    }

    /**
     * @param p1 Point
     * @param p2 Point
     * @param p3 Point
     * @return 0 if collinear, 1 if going with the clock, 2 if going counter- clock.
     */
    public int orien(Point p1, Point p2, Point p3) {
        // this is the idea of the algorithm ->>See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        double val;
        // formula of the orientation.
        val = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX())
                - ((p2.getX() - p1.getX()) * (p3.getY() - p2.getY()));
        // collinear
        if (val == 0) {
            return 0;
        }
        // clockwise
        if (val > 0) {
            return 1;
        } else {
            //counter-clockwise.
            return -1;
        }
    }

    /**
     * @param other Line.
     * @return true\false.
     */
    public boolean isIntersecting(Line other) {
        int orein1 = orien(this.point1, this.point2, other.point1);
        int orein2 = orien(this.point1, this.point2, other.point2);
        int orein3 = orien(other.point1, other.point2, this.point1);
        int orein4 = orien(other.point1, other.point2, this.point2);
        // if the lines dont have the same slope, return true else return false.
        return orein1 != orein2 && orein3 != orein4;
    }

    /**
     * @param other line that his vertical
     * @return Point intersection.
     */
    public Point intersectionWithVertical(Line other) {
        // if both line are vertical return null.
        if (this.point2.getX() == this.point1.getX()) {
            return null;
        }
        double slope1 = (this.point2.getY() - this.point1.getY()) / (this.point2.getX() - this.point1.getX());
        double equation1 = -slope1 * (this.point1.getX()) + this.point1.getY();
        double y = slope1 * other.point1.getX() + equation1;
        if (((y >= other.point1.getY()) && y <= other.point2.getY())
                || ((y >= other.point2.getY()) && y <= other.point1.getY())) {
            return new Point(Math.round(other.point1.getX()), Math.round(y));
        }
        // if there is no intersection return null.
        return null;
    }

    /**
     * @param other Line.
     * @return Point.
     * notice: if the lines is intersecting in the end point of one line and in the start point of second line
     * but the slope is equal, i did that they don't be count as a intersecting.
     */
    public Point intersectionWith(Line other) {
        // if the line dosen't even meet, return null.
        if (!isIntersecting(other)) {
            return null;
        }
        // for the case that on line is vertical, we cannot calculate the slope because it is infinity
        if (this.point2.getX() == this.point1.getX()) {
            return other.intersectionWithVertical(this);
        } else if (other.point2.getX() == other.point1.getX()) {
            return intersectionWithVertical(other);
        }
        // slope of the first line.
        double slope1 = (this.point2.getY() - this.point1.getY()) / (this.point2.getX() - this.point1.getX());
        //slope of the second line.
        double slope2 = (other.point2.getY() - other.point1.getY()) / (other.point2.getX() - other.point1.getX());
        // equation of the Line1
        double equation1 = -slope1 * (this.point1.getX()) + this.point1.getY();
        // equation of the Line 2
        double equation2 = -slope2 * (other.point1.getX()) + other.point1.getY();
        // Calculate the new X and Y of the point.
        double newX = (equation2 - equation1) / (slope1 - slope2);
        double newY = (slope1 * newX) + equation1;
        return new Point(Math.round(newX), Math.round(newY));
    }

    /**
     * @param other Line
     * @return return true is the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        return ((this.point1 == other.start()) && (this.point2 == other.end()))
                || ((this.point2 == other.start()) && (this.point1 == other.end()));
    }

    /**
     * @param rect Object.
     * @return Closest Point intersection.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        double min;
        Point minPoint;
        // create list of intersection Point between the Line and the rectangle.
        List points = rect.intersectionPoints(new Line(this.start(), this.end()));
        if (points.isEmpty()) {
            return null;
        }
        // check which one have the minimum distance between the start point to the intersection point.
        minPoint = (Point) points.get(0);
        min = rect.getUpperLeft().distance(minPoint);
        for (int i = 0; i < points.size(); i++) {
            // if there is a distance that lower from the min, set him to be the minimum and save that docs.
            if (rect.getUpperLeft().distance((Point) points.get(i)) < min) {
                minPoint = (Point) points.get(i);
                min = rect.getUpperLeft().distance((Point) points.get(i));
            }
        }
        return minPoint;
    }


}

