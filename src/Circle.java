/*
CLASS: Circle
DESCRIPTION: A circular shape defined by a center point and radius.
*/
import java.awt.*;

class Circle extends Shape {
    private final double radius;
    private Point velocity;
    private final Color color;

    public Circle(Point center, double radius, Color color) {
        super(center);
        this.radius = radius;
        this.velocity = new Point(0, 0);
        this.color = color;
    }


    public boolean contains(Point p) {
        double distance = Math.sqrt(
                Math.pow(position.x - p.x, 2) +
                        Math.pow(position.y - p.y, 2)
        );
        return distance <= radius;
    }


    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }


    public Point[] getPerimeterPoints() {
        int numPoints = 16;
        Point[] points = new Point[numPoints];

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            double x = position.x + radius * Math.cos(angle);
            double y = position.y + radius * Math.sin(angle);
            points[i] = new Point(x, y);
        }

        return points;
    }


    public void paint(Graphics brush) {
        brush.setColor(color);
        brush.fillOval(
                (int)(position.x - radius),
                (int)(position.y - radius),
                (int)(2 * radius),
                (int)(2 * radius)
        );
    }


    public void move() {
        position.x += velocity.x;
        position.y += velocity.y;
    }


    public void setVelocity(Point vel) {
        velocity = vel;
    }


    public double getRadius() {
        return radius;
    }
}