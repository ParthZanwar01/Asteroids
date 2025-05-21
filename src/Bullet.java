/*
CLASS: Bullet
DESCRIPTION: A projectile fired by the ship.
*/
import java.awt.*;

class Bullet extends Circle {

    public Bullet(Point position, double direction, Point shipVelocity) {
        super(position, 2, Color.RED);


        double speed = 5.0;
        double bulletVx = speed * Math.cos(Math.toRadians(direction));
        double bulletVy = speed * Math.sin(Math.toRadians(direction));


        setVelocity(new Point(bulletVx + shipVelocity.x, bulletVy + shipVelocity.y));


    }

    @Override
    public void move() {
        super.move();
    }


    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.red);
        brush.fillOval((int)(position.x - getRadius()), (int)(position.y - getRadius()), (int)(getRadius() * 2), (int)(getRadius() * 2));
    }
}