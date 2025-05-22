/*
CLASS: Polygon
DESCRIPTION: A polygon is a sequence of points in space defined by a set of
             such points, an offset, and a rotation. The offset is the
             distance between the origin and the center of the shape.
             The rotation is measured in degrees, 0-360.
             Now extends the Shape class.
*/
import java.awt.*;

class Polygon extends Shape {
  private Point[] shape;
  public double rotation;
  private Point velocity;

  public Polygon(Point[] inShape, Point inPosition, double inRotation) {
    super(inPosition);
    shape = inShape.clone();
    rotation = inRotation;
    velocity = new Point(0, 0);


    Point origin = new Point(shape[0].x, shape[0].y);
    for (Point p : shape) {
      if (p.x < origin.x) origin.x = p.x;
      if (p.y < origin.y) origin.y = p.y;
    }


    for (Point p : shape) {
      p.x -= origin.x;
      p.y -= origin.y;
    }
  }


  public Point[] getPoints() {
    Point center = findCenter();
    Point[] points = new Point[shape.length];
    for (int i = 0; i < shape.length; i++) {
      double x = ((shape[i].x-center.x) * Math.cos(Math.toRadians(rotation))) - ((shape[i].y-center.y) * Math.sin(Math.toRadians(rotation))) + center.x + position.x;
      double y = ((shape[i].x-center.x) * Math.sin(Math.toRadians(rotation))) + ((shape[i].y-center.y) * Math.cos(Math.toRadians(rotation))) + center.y + position.y;
      points[i] = new Point(x, y);
    }
    return points;
  }


  public boolean contains(Point point) {
    Point[] points = getPoints();
    double crossingNumber = 0;
    for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
      if ((((points[i].x < point.x) && (point.x <= points[j].x)) || ((points[j].x < point.x) && (point.x <= points[i].x))) && (point.y > points[i].y + (points[j].y-points[i].y)/(points[j].x - points[i].x) * (point.x - points[i].x))) {
        crossingNumber++;
      }
    }
    return crossingNumber%2 == 1;
  }

  public void rotate(int degrees) {
    rotation = (rotation+degrees)%360;
  }


  public void paint(Graphics brush) {
    Point[] points = getPoints();
    int[] xPoints = new int[points.length];
    int[] yPoints = new int[points.length];

    for (int i = 0; i < points.length; i++) {
      xPoints[i] = (int)points[i].x;
      yPoints[i] = (int)points[i].y;
    }

    brush.drawPolygon(xPoints, yPoints, points.length);
  }


  public void move() {
    position.x += velocity.x;
    position.y += velocity.y;
  }


  public void setVelocity(Point vel) {
    velocity = vel;
  }


  public Point getVelocity() {
    return velocity;
  }


  public double getPerimeter() {
    double perimeter = 0;
    Point[] points = getPoints();
    for (int i = 0, j = 1; i < points.length; i++, j=(j+1)%points.length) {
      perimeter += Math.sqrt(Math.pow(points[j].x - points[i].x, 2) + Math.pow(points[j].y - points[i].y, 2));
    }
    return perimeter;
  }


  public Point[] getPerimeterPoints() {
    Point[] points = getPoints();
    int numPointsPerEdge = 5;
    Point[] perimeterPoints = new Point[shape.length * numPointsPerEdge];

    int index = 0;
    for (int i = 0, j = 1; i < points.length; i++, j=(j+1)%points.length) {

      perimeterPoints[index++] = points[i];


      for (int k = 1; k < numPointsPerEdge; k++) {
        double ratio = (double)k / numPointsPerEdge;
        double x = points[i].x + ratio * (points[j].x - points[i].x);
        double y = points[i].y + ratio * (points[j].y - points[i].y);
        perimeterPoints[index++] = new Point(x, y);
      }
    }

    return perimeterPoints;
  }

  /*
  Private helper methods
  */


  private double findArea() {
    double sum = 0;
    for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
      sum += shape[i].x*shape[j].y-shape[j].x*shape[i].y;
    }
    return Math.abs(sum/2);
  }


  private Point findCenter() {
    Point sum = new Point(0, 0);
    for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
      sum.x += (shape[i].x + shape[j].x) * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
      sum.y += (shape[i].y + shape[j].y) * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
    }
    double area = findArea();
    return new Point(Math.abs(sum.x/(6*area)), Math.abs(sum.y/(6*area)));
  }
}