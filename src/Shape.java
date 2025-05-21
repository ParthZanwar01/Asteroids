/*
CLASS: Shape
DESCRIPTION: An abstract class that serves as the parent class for Polygon and Circle.
             Defines common methods for all shapes.
*/
import java.awt.*;

abstract class Shape {
    public Point position;

    public Shape(Point position) {
        this.position = position;
    }


    abstract public boolean contains(Point p);


    public boolean intersects(Shape other) {

        Point[] perimeterPoints = getPerimeterPoints();


        for (Point p : perimeterPoints) {
            if (other.contains(p)) {
                return true;
            }
        }


        Point[] otherPerimeterPoints = other.getPerimeterPoints();


        for (Point p : otherPerimeterPoints) {
            if (this.contains(p)) {
                return true;
            }
        }

        return false;
    }


    abstract public double getPerimeter();


    abstract public Point[] getPerimeterPoints();


    abstract public void paint(Graphics brush);


    abstract public void move();
}