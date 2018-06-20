/**
 * @autor: dvir segev
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Class of Shape Rectangle.
 */

public class Rectangle {
    private double width;
    private double height;
    private Point upperLeft;

    /**
     * @param upperLeft Start point.
     * @param width     of the rectangle.
     * @param height    of the rectangle.
     * @apiNote Create a new rectangle with location and width/height.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.width = width;
        this.height = height;
        this.upperLeft = upperLeft;
    }


    /**
     * @param line Line of the ball.
     * @return List of all intersection Points.
     * Return a (possibly empty) List of intersection points
     * with the specified line (type points).
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        Point intersection;
        // build the  List interface- ArrayList Class.
        List points = new ArrayList();
        // build the  upper rib.
        Line upperRib = new Line(this.upperLeft.getX(), this.upperLeft.getY(),
                this.upperLeft.getX() + this.width,
                upperLeft.getY());
        // build the  left rib
        Line leftRib = new Line(this.upperLeft.getX(), this.upperLeft.getY(), upperLeft.getX(),
                upperLeft.getY() + this.height);
        // build the down rib
        Line downRib = new Line(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height,
                this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        // build the right rib
        Line rightRib = new Line(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height,
                this.upperLeft.getX() + this.width, this.upperLeft.getY());
        // add to the intersection list the points that intersect with the lines.
        if (upperRib.isIntersecting(line)) {
            intersection = upperRib.intersectionWith(line);
            points.add(intersection);
        }
        if (downRib.isIntersecting(line)) {
            intersection = downRib.intersectionWith(line);
            points.add(intersection);
        }

        if (leftRib.isIntersecting(line)) {
            intersection = leftRib.intersectionWith(line);
            points.add(intersection);
        }
        if (rightRib.isIntersecting(line)) {
            intersection = rightRib.intersectionWith(line);
            points.add(intersection);
        }
        // return the list.
        return points;
    }

    /**
     * @return width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return height.
     */
    public double getHeight() {
        return this.height;
    }


    /**
     * @param width1 of the rectangle.
     */
    public void setWidth(double width1) {
        this.width = width1;
    }

    /**
     * @param height1 of the rectangle.
     */
    public void setHeight(double height1) {
        this.height = height1;
    }

    /**
     * @param upperLeft1 set start point of the rectangle.
     */
    public void setUpperLeft(Point upperLeft1) {
        this.upperLeft = upperLeft1;
    }

    /**
     * @return point of the start screen.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return up rib.
     */
    public Line getUpperRib() {
        return new Line(this.upperLeft.getX(), this.upperLeft.getY(),
                this.upperLeft.getX() + this.width,
                upperLeft.getY());
    }

    /**
     * @return left rib.
     */
    public Line getLeftRib() {
        return new Line(this.upperLeft.getX(), this.upperLeft.getY(), upperLeft.getX(),
                upperLeft.getY() + this.height);
    }

    /**
     * @return down rib.
     */
    public Line getDownRib() {
        return new Line(this.upperLeft.getX(), this.upperLeft.getY() + this.height,
                this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
    }

    /**
     * @return right rib.
     */
    public Line getRightRib() {
        return new Line(this.upperLeft.getX() + this.width, this.upperLeft.getY(),
                this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
    }

    /**
     * @return the down left Point.
     */
    public Point getDownLeft() {
        return new Point(upperLeft.getX(),
                upperLeft.getY() + this.height);
    }

    /**
     * @return the down - right Point.
     */
    public Point getDownRight() {
        return new Point(upperLeft.getX() + this.height,
                upperLeft.getY() + this.height);
    }

    /**
     * @return the upper - right point
     */
    public Point getUpperRight() {
        return new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
    }
}
